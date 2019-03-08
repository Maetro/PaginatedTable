package com.paginatedtable.common.util;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

/**
 * The interface User type request.
 */
public interface IUserTypeRequest {


    /**
     * Execute for entity string.
     *
     * @param mockMvc    the mock mvc
     * @param uri        the uri
     * @param httpMethod the http method
     * @param jsonEntity the json entity
     * @return the string
     * @throws Exception the exception
     */
    String executeForEntity(MockMvc mockMvc, URI uri, HttpMethod httpMethod, String jsonEntity) throws Exception;

    /**
     * Execute for results result actions.
     *
     * @param mockMvc    the mock mvc
     * @param uri        the uri
     * @param httpMethod the http method
     * @param jsonEntity the json entity
     * @return the result actions
     * @throws Exception the exception
     */
    ResultActions executeForResults(MockMvc mockMvc, URI uri, HttpMethod httpMethod, String jsonEntity) throws Exception;

}
