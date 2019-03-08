package com.paginatedtable.service.common.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FilterQueryDTO implements Serializable {

    private static final long serialVersionUID = 6644920345092982035L;

    private Integer page;

    private Integer numberOfElements;

    private SortStatus sortStatus;

    private List<String> searchQuery;

}
