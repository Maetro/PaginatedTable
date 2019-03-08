package com.paginatedtable.model.common.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public class FilterResolutionStrategyFactory {

    private FilterResolutionStrategyFactory(){}

    public static FilterResolutionStrategy getInstance(CriteriaBuilder criteriaBuilder, Root table, String filterValue){
        if (filterValue.contains("..")){
            return new FilterResolutionRange(criteriaBuilder, table, filterValue);
        }
        if (filterValue.contains(">") || filterValue.contains(">=") ||
                filterValue.contains("<") || filterValue.contains("<=") || filterValue.contains("==")){
            return new FilterResolutionComparator(criteriaBuilder, table, filterValue);
        } else {
            return new FilterResolutionFieldByContent(criteriaBuilder, table, filterValue);
        }

    }
}
