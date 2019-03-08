package com.paginatedtable.model.common.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FilterResolutionRange extends FilterResolutionStrategy {

    public FilterResolutionRange(CriteriaBuilder criteriaBuilder, Root table, String filterValue) {
        super(criteriaBuilder, table, filterValue);
    }

    @Override
    public Predicate createFilter(String filter) {

        String[] splittedFilter = getFilterValue().split("\\.\\.");
        String minValue = splittedFilter[0];
        String maxValue = splittedFilter[1];
        return criteriaBuilder.between(table.get(filter), Integer.valueOf(minValue), Integer.valueOf(maxValue));

    }


}
