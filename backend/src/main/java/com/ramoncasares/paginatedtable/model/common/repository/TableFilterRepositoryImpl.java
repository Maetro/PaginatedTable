package com.ramoncasares.paginatedtable.model.common.repository;

import com.ramoncasares.paginatedtable.model.common.util.FilterResolutionStrategy;
import com.ramoncasares.paginatedtable.model.common.util.FilterResolutionStrategyFactory;
import com.ramoncasares.paginatedtable.service.common.dto.FilterQueryDTO;
import com.ramoncasares.paginatedtable.service.common.dto.FilterResponse;
import com.ramoncasares.paginatedtable.service.common.dto.IdentifierFilterDataDTO;
import com.ramoncasares.paginatedtable.service.common.dto.SortStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class TableFilterRepositoryImpl<E, D, Id> implements TableFilterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FilterResponse getListOfElementsByFilter(FilterQueryDTO filterQueryDTO) throws ParseException {
        FilterResponse response = getNewInstanceOfResponse();
        List<E> filteredList = new ArrayList<>();

        if (CollectionUtils.isEmpty(filterQueryDTO.getSearchQuery())) {
            queryWithOrderBy(filteredList, filterQueryDTO);
        } else {
            boolean first = true;
            for (String searchQuery : filterQueryDTO.getSearchQuery()) {

                String[] parts = searchQuery.split(";");
                for (String completeFilter : parts) {
                    filterPartProcess(filterQueryDTO, filteredList, completeFilter, first);
                }
                if (first) {
                    first = false;
                }
                if (filteredList.size() == 0) {
                    break;
                }
            }
        }
        int firstElement = (filterQueryDTO.getPage()) * filterQueryDTO.getNumberOfElements();
        int lastElement = filteredList.size() < firstElement + filterQueryDTO.getNumberOfElements() ?
                filteredList.size() : firstElement + filterQueryDTO.getNumberOfElements();
        if (firstElement < lastElement) {
            List<E> temp = filteredList.subList(firstElement, lastElement);
            List<D> auditResult = new ArrayList<>(this.adaptEntityListToDTO(temp));

            response.setData(auditResult);
            response.setNumberOfElements(filteredList.size());
        } else {
            response.setData(new ArrayList());
            response.setNumberOfElements(0);
        }

        return response;
    }

    private void filterPartProcess(FilterQueryDTO filterQueryDTO, List<E> filteredList, String completeFilter, boolean first) throws ParseException {
        if (StringUtils.isNotBlank(completeFilter)) {
            if (completeFilter.contains(":")) {
                filterWithFields(filteredList, completeFilter, filterQueryDTO.getSortStatus(), first);
            } else {
                filterWithoutFields(filteredList, completeFilter, filterQueryDTO.getSortStatus(), first);
            }
        }
    }

    private void filterWithoutFields(List<E> filteredList, String completeFilter,
                                     SortStatus sortStatus, boolean first) {
        StringBuilder sortPart = new StringBuilder();
        if (sortStatus != null && StringUtils.isNotBlank(sortStatus.getDirection())) {
            sortPart.append(" ORDER BY ");
            sortPart.append(sortStatus.getActive());
            sortPart.append(" ");
            sortPart.append(sortStatus.getDirection());
        }

        Query q = entityManager.createNativeQuery(this.getNativeQueryForAllFields(completeFilter, sortPart.toString()));
        List<E> result = q.getResultList();
        filteredList.addAll(result);

    }

    private void queryWithOrderBy(List<E> filteredList, FilterQueryDTO filterQueryDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery().distinct(true);
        Root table = criteriaQuery.from(getEntityTableClass());
        criteriaQuery.select(table);
        applyOrderByIfIsDefined(filterQueryDTO.getSortStatus(), criteriaBuilder, criteriaQuery, table);
        Query query = entityManager.createQuery(criteriaQuery);
        List<E> result = query.getResultList();
        filteredList.addAll(result);
    }


    private void filterWithFields(List<E> filteredList, String completeFilter, SortStatus sortStatus, boolean first) throws ParseException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery().distinct(true);
        Root table = criteriaQuery.from(getEntityTableClass());
        applyOrderByIfIsDefined(sortStatus, criteriaBuilder, criteriaQuery, table);
        criteriaQuery.select(table);
        String filter = completeFilter.substring(0, completeFilter.indexOf(':')).trim();
        String filterValue = completeFilter.substring(completeFilter.indexOf(':') + 1).trim();
        FilterResolutionStrategy filterResolutionStrategy = FilterResolutionStrategyFactory.getInstance(
                criteriaBuilder, table, filterValue);
        Predicate whereSection = filterResolutionStrategy.createFilter(filter);
        if (first) {
            criteriaQuery.where(whereSection);
            Query query = entityManager.createQuery(criteriaQuery);
            List result = query.getResultList();
            filteredList.addAll(result);
        } else {
            IdentifierFilterDataDTO identifiers = getEntitiesIdentifiers(filteredList);
            Expression exp = table.get(identifiers.getIdentifierFieldName());
            Predicate extraFilter = exp.in(identifiers.getIdentifierFieldFilterList());
            criteriaQuery.where(criteriaBuilder.and(extraFilter, whereSection));
            Query query = entityManager.createQuery(criteriaQuery);
            filteredList.clear();
            filteredList.addAll(query.getResultList());
        }
    }

    private void applyOrderByIfIsDefined(SortStatus sortStatus, CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery, Root auditUser) {
        if (sortStatus != null && StringUtils.isNotBlank(sortStatus.getDirection())) {
            if (sortStatus.getDirection().equals("asc")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(auditUser.get(sortStatus.getActive())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(auditUser.get(sortStatus.getActive())));
            }
        }
    }

    protected abstract Collection<D> adaptEntityListToDTO(List<E> temp);

    protected abstract IdentifierFilterDataDTO getEntitiesIdentifiers(List<E> temp);

    protected abstract String getNativeQueryForAllFields(String completeFilter, String sort);

    protected abstract FilterResponse getNewInstanceOfResponse();

    protected abstract Class getEntityTableClass();


}
