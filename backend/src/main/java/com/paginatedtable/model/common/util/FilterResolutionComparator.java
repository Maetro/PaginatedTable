package com.paginatedtable.model.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FilterResolutionComparator extends FilterResolutionStrategy {

    public FilterResolutionComparator(CriteriaBuilder criteriaBuilder, Root table, String filterValue) {
        super(criteriaBuilder, table, filterValue);
    }

    @Override
    public Predicate createFilter(String filter) {
        Predicate fieldByComparatorFilter = null;
        if (getFilterValue().contains(">=")){
            String filterValue = getFilterValue().substring(2).trim();
            if (StringUtils.isNoneBlank(filterValue)){
                fieldByComparatorFilter = criteriaBuilder.greaterThanOrEqualTo(table.get(filter), filterValue);
            }
        } else if (getFilterValue().contains(">")){
            String filterValue = getFilterValue().substring(1).trim();
            if (StringUtils.isNoneBlank(filterValue)) {
                fieldByComparatorFilter = criteriaBuilder.greaterThan(table.get(filter), filterValue);
            }
        } else if (getFilterValue().contains("<=")){
            String filterValue = getFilterValue().substring(2).trim();
            if (StringUtils.isNoneBlank(filterValue)) {
                fieldByComparatorFilter = criteriaBuilder.lessThanOrEqualTo(table.get(filter), filterValue);
            }
        } else if (getFilterValue().contains("<")){
            String filterValue = getFilterValue().substring(1).trim();
            if (StringUtils.isNoneBlank(filterValue)) {
                fieldByComparatorFilter = criteriaBuilder.lessThan(table.get(filter), filterValue);
            }
        } else {
            String filterValue = getFilterValue().substring(2).trim();
            if (StringUtils.isNoneBlank(filterValue)) {
                fieldByComparatorFilter = criteriaBuilder.equal(table.get(filter), filterValue);
            }
        }
        return fieldByComparatorFilter;
    }


}
