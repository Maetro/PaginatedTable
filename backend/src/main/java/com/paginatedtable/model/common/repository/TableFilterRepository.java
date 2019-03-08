package com.paginatedtable.model.common.repository;

import com.paginatedtable.service.common.dto.FilterQueryDTO;
import com.paginatedtable.service.common.dto.FilterResponse;

import java.text.ParseException;

/**
 * The interface Table filter repository.
 */
public interface TableFilterRepository {

    /**
     * Gets list of elements by filter.
     *
     * @param filterQueryDTO the filter query dto
     * @return the list of elements by filter
     * @throws ParseException the parse exception
     */
    FilterResponse getListOfElementsByFilter(FilterQueryDTO filterQueryDTO) throws ParseException;

}
