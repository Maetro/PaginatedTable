package com.paginatedtable.model.common.util;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class FilterResolutionFieldByContent extends FilterResolutionStrategy {

    public FilterResolutionFieldByContent(CriteriaBuilder criteriaBuilder, Root table, String filterValue) {
        super(criteriaBuilder, table, filterValue);
    }

    @Override
    public Predicate createFilter(String filter) throws ParseException {
        Predicate fieldByContentFilter = null;
        From actualTable = table;
        if (filter.contains(".")) {
            String[] temp = filter.split("\\.");
            for (int i = 0; i < temp.length - 1; i++) {
                actualTable = actualTable.join(temp[i], JoinType.INNER);
            }
            filter = temp[temp.length - 1];
        }
        actualTable.get(filter);
        if (actualTable.get(filter).getJavaType().equals(Long.class)) {
            fieldByContentFilter = criteriaBuilder.equal(actualTable.get(filter), getFilterValue());
        } else if (actualTable.get(filter).getJavaType().equals(Date.class)) {
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Date date = null;
            try {
                date = dt.parse(getFilterValue().trim());
                Calendar plusMinute = Calendar.getInstance();
                plusMinute.setTime(date);
                plusMinute.add(Calendar.MINUTE, 1);
                fieldByContentFilter = criteriaBuilder.between(actualTable.get(filter), date, plusMinute.getTime());
            } catch (ParseException e) {
                dt = new SimpleDateFormat("dd/MM/yyyy");
                date = dt.parse(getFilterValue().trim());
                Calendar plusDay = Calendar.getInstance();
                plusDay.setTime(date);
                plusDay.add(Calendar.DAY_OF_YEAR, 1);
                fieldByContentFilter = criteriaBuilder.between(actualTable.get(filter), date, plusDay.getTime());
            }
        } else if (actualTable.get(filter).getJavaType().isEnum()) {
            Object enumValue = null;
            for (Object type : actualTable.get(filter).getJavaType().getEnumConstants()) {
                if (type.toString().equals(getFilterValue())) {
                    enumValue = type;
                }
            }
            fieldByContentFilter = criteriaBuilder.equal(actualTable.get(filter), enumValue);
        } else if (actualTable.get(filter).getJavaType().equals(Boolean.class)) {
            fieldByContentFilter = criteriaBuilder.equal(actualTable.get(filter), Boolean.valueOf(getFilterValue()));
        } else {
            fieldByContentFilter = criteriaBuilder.like(actualTable.get(filter), '%' + getFilterValue() + '%');
        }
        return fieldByContentFilter;
    }
}
