package com.ramoncasares.paginatedtable.service.common.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class FilterResponse<T> {

    protected List<T> data;

    private Integer numberOfElements;

}
