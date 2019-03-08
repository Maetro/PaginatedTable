package com.paginatedtable.service.common.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class IdentifierFilterDataDTO<T> implements Serializable {

    private String identifierFieldName;

    private List<T> identifierFieldFilterList;

}
