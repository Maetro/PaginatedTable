package com.paginatedtable.model.common.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;

public abstract class FilterResolutionStrategy {

    protected CriteriaBuilder criteriaBuilder;

    protected Root table;

    protected String filterValue;

    FilterResolutionStrategy(CriteriaBuilder criteriaBuilder, Root table, String filterValue){
        this.criteriaBuilder = criteriaBuilder;
        this.filterValue = filterValue;
        this.table = table;
    }

    public abstract Predicate createFilter(String filterValue) throws ParseException;

    public String getFilterValue() {
        return filterValue;
    }

}
