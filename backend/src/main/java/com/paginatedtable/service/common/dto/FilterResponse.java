package com.paginatedtable.service.common.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class FilterResponse<T> {

    protected List<T> data;

    private Integer numberOfElements;

    public FilterResponse() {
        this.data = new ArrayList<>();
        this.numberOfElements = 0;
    }
}
