package com.ramoncasares.paginatedtable.common.util.usertyperequest;

import com.ramoncasares.paginatedtable.common.util.IUserTypeRequest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

public class UnauthenticatedUserTypeRequest implements IUserTypeRequest {

    private static IUserTypeRequest singleton;

    private UnauthenticatedUserTypeRequest() { }

    public static IUserTypeRequest getUserTypeRequestInstance() {
        if (singleton == null) {
            singleton = new UnauthenticatedUserTypeRequest();
        }
        return singleton;
    }

    @Override
    public String executeForEntity(MockMvc mockMvc, URI uri, HttpMethod httpMethod, String jsonEntity) throws Exception {

        String responseStringEntity = null;
        switch (httpMethod) {
            case GET:
                responseStringEntity = mockMvc.perform(request(httpMethod, uri)
                        .header("Content-Type", "application/json"))
                        .andReturn().getResponse().getContentAsString();
                break;
            case PUT:
            case POST:
            case DELETE:
                responseStringEntity = mockMvc.perform(request(httpMethod, uri.toString(), jsonEntity)).andReturn()
                        .getResponse().getContentAsString();
                break;
            default:
                break;
        }

        return responseStringEntity;
    }

    @Override
    public ResultActions executeForResults(MockMvc mockMvc, URI uri, HttpMethod httpMethod, String jsonEntity) throws Exception {
        ResultActions resultActions = null;
        switch (httpMethod) {
            case GET:
                resultActions = mockMvc.perform(request(httpMethod, uri)
                        .header("Content-Type", "application/json"));
                break;
            case PUT:
            case POST:
            case DELETE:
                resultActions = mockMvc.perform(request(httpMethod, uri.toString(), jsonEntity));
                break;
            default:
                break;
        }

        return resultActions;
    }

}
