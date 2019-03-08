package com.paginatedtable.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paginatedtable.common.util.UserTypeEnum;
import com.paginatedtable.common.util.IUserTypeRequest;
import com.paginatedtable.common.util.UserTypeEnum;
import com.paginatedtable.common.util.UserTypeRequestFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public final class PaginatedTableControllerTestHelper {

    private PaginatedTableControllerTestHelper() {
    }

    /**
     * Try HTTP request with user type.
     *
     * @param urlTest    the url test
     * @param httpMethod the http method
     * @return the response entity
     */
    public static String tryHTTPRequestWithUserTypeToJSON(final MockMvc mockMvc, final String urlTest, final HttpMethod httpMethod) {

        return tryHTTPRequestWithUserTypeToJSON(mockMvc, urlTest, httpMethod,null);
    }


    private static String tryHTTPRequestWithUserTypeToJSON(final MockMvc mockMvc, final String urlTest, final HttpMethod httpMethod,
                                                            final String requestEntityJson) {
        IUserTypeRequest userTypeRequest = UserTypeRequestFactory.getHttpRequest(UserTypeEnum.ANONIMO);
        String entity = null;
        try {
            URI uri = new URI(urlTest);
            entity = userTypeRequest.executeForEntity(mockMvc, uri, httpMethod, requestEntityJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return entity;
    }

    public static ResultActions tryHTTPRequestWithUserTypeToAnalyseResult(final MockMvc mockMvc, final String urlTest,
                                                                          final HttpMethod httpMethod) {
        return tryHTTPRequestWithUserTypeToAnalyseResult(mockMvc, urlTest, httpMethod, null);
    }

    public static ResultActions tryHTTPRequestWithUserTypeToAnalyseResult(final MockMvc mockMvc, final String urlTest,
                                                                          final HttpMethod httpMethod, String requestEntityJson) {
        IUserTypeRequest userTypeRequest = UserTypeRequestFactory.getHttpRequest(UserTypeEnum.ADMIN);
        ResultActions entity = null;
        try {
            URI uri = new URI(urlTest);
            entity = userTypeRequest.executeForResults(mockMvc, uri, httpMethod, requestEntityJson);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return entity;
    }

    protected static <T> T extractEntityFromBody(final ResponseEntity<String> entity, final Class<T> type)
            throws IOException, JsonParseException, JsonMappingException {
        ObjectMapper mapper = getObjectMapper();
        String body = entity.getBody();
        T value = null;
        if (body != null) {
            value = mapper.readValue(body, type);
        }
        return value;
    }

    public static <T> T extractEntityFromBody(final String entityJson,
                                                       final Class<T> typeReference) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.readValue(entityJson, typeReference);
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper;
    }

    public static <T> T tryHTTPRequestWithUserTypeToObject(final MockMvc mockMvc, final String urlTest, final HttpMethod httpMethod,
                                                           final Class<T> typeReference) {
        T result = null;
        try {
            String resultJson = tryHTTPRequestWithUserTypeToJSON(mockMvc, urlTest, httpMethod, null);

            result = extractEntityFromBody(resultJson, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static <T> T tryHTTPRequestWithUserTypeToObject(final MockMvc mockMvc, final String urlTest, final HttpMethod httpMethod,
                                                            final UserTypeEnum userType, final String requestEntityJson, final Class<T> typeReference) {
        T result = null;
        try {
            result = extractEntityFromBody(tryHTTPRequestWithUserTypeToJSON(mockMvc, urlTest, httpMethod, requestEntityJson), typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> List<T> tryHTTPRequestWithUserTypeToObjectList(final MockMvc mockMvc, final String urlTest, final HttpMethod httpMethod,
                                                                     final Class<T> typeReference) throws ClassNotFoundException {
        List<T> result = null;
        try {
            String resultJson = tryHTTPRequestWithUserTypeToJSON(mockMvc, urlTest, httpMethod, null);

            Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + typeReference.getName() + ";");
            result = Arrays.asList(extractEntityListFromBody(resultJson, arrayClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static <T> List<T> tryHTTPRequestWithUserTypeToObjectList(final MockMvc mockMvc, final String urlTest, final HttpMethod httpMethod,
                                                                      final String requestEntityJson, final Class<T> typeReference) throws ClassNotFoundException {
        List<T> result = null;
        try {
            String resultJson = tryHTTPRequestWithUserTypeToJSON(mockMvc, urlTest, httpMethod, requestEntityJson);
            Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + typeReference.getName() + ";");
            result = Arrays.asList(extractEntityListFromBody(resultJson, arrayClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static <T> T[] extractEntityListFromBody(final String body, final Class<T[]> type)
            throws IOException {
        ObjectMapper mapper = getObjectMapper();
        T[] value = null;
        if (body != null) {
            value = mapper.readValue(body, type);
        }
        return value;
    }

    public static <T> List<T> extractListFromJsonBody(final String jsonBody, final Class<T> typeReference) throws IOException, ClassNotFoundException {
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + typeReference.getName() + ";");
        return Arrays.asList(extractEntityListFromBody(jsonBody, arrayClass));
    }

    public static <T> String toJson(final T entity) throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.writeValueAsString(entity);
    }

    public static <T> String toJson(final List<T> entityList) throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.writeValueAsString(entityList);
    }

}
