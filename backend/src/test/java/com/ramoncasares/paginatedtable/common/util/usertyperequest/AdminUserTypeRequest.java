package com.ramoncasares.paginatedtable.common.util.usertyperequest;

import com.ramoncasares.paginatedtable.common.util.IUserTypeRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * The Class AdminUserTypeRequest.
 */
public class AdminUserTypeRequest implements IUserTypeRequest {

    private MultiValueMap<String, String> headers;

    private static AdminUserTypeRequest singleton;

    private String token;

    public static IUserTypeRequest getUserTypeRequestInstance() {
        if (singleton == null) {
            singleton = new AdminUserTypeRequest();
        }
        return singleton;
    }

    private AdminUserTypeRequest() {
        this.headers = loginWithAdminUser();
    }

    private MultiValueMap<String, String> loginWithAdminUser(){

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer FakeTokenForAdmin");
        return headers;
    }

    @Override
    public String executeForEntity(MockMvc mockMvc, URI uri, HttpMethod httpMethod, String requestEntity) throws Exception {
        String responseStringEntity = null;
        switch (httpMethod) {
            case GET:
            case DELETE:
                responseStringEntity = mockMvc.perform(request(httpMethod, uri)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer FakeTokenForAdmin"))
                        .andReturn().getResponse().getContentAsString();
                break;
            case PUT:
            case POST:
                responseStringEntity = mockMvc.perform(request(httpMethod, uri.toString(), requestEntity)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer FakeTokenForAdmin"))
                        .andReturn().getResponse().getContentAsString();
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
            case DELETE:
                resultActions = mockMvc.perform(request(httpMethod, uri)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer FakeTokenForAdmin"));
                break;
            case PUT:
                resultActions = mockMvc.perform(put(uri.toString()).contentType(MediaType.APPLICATION_JSON).content(jsonEntity)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer FakeTokenForAdmin"));
                break;
            case POST:
                resultActions = mockMvc.perform(post(uri.toString()).contentType(MediaType.APPLICATION_JSON).content(jsonEntity)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer FakeTokenForAdmin"));
                break;
            default:
                break;
        }

        return resultActions;
    }

}
