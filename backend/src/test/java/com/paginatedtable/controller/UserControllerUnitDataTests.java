package com.paginatedtable.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paginatedtable.common.AbstractUnitControllerTests;
import com.paginatedtable.common.CreateTestMocksHelper;
import com.paginatedtable.common.PaginatedTableControllerTestHelper;
import com.paginatedtable.common.util.UserTypeEnum;
import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.service.common.dto.FilterQueryDTO;
import com.paginatedtable.service.user.dto.UserDTO;
import com.paginatedtable.service.user.dto.UserFilterResponseDTO;
import com.paginatedtable.service.user.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.management.InstanceNotFoundException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("User Controller Unit Tests")
public class UserControllerUnitDataTests extends AbstractUnitControllerTests{

    private final Logger logger = LoggerFactory.getLogger(UserControllerUnitDataTests.class);

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    private List<RoleEntity> rolesList;

    private List<UserEntity> usersList;
    private List<UserDTO> userListDTOs;

    /**
     * The Flowable management service.
     */
    @Autowired
    protected MockMvc mockMvc;

    @Override
    protected void customBeforeClass() throws Exception {
        CreateTestMocksHelper.InitUserDataResponse initTestData = CreateTestMocksHelper.instance().initUserData(null, null);
        rolesList = initTestData.getRolesList();
        usersList = initTestData.getUsersList();
        userListDTOs = initTestData.getUserListDTOs();
    }

    @Test
    @DisplayName("testGetUsers()")
    public void testGetUsers() throws ClassNotFoundException {
        when(userService.getAllUsers()).thenReturn(this.userListDTOs);
        List<UserDTO> result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToObjectList(this.mockMvc,
                "/user", HttpMethod.GET, UserDTO.class);
        for(UserDTO user : this.userListDTOs) {
            assertTrue(result.contains(user));
        }
    }

    @Test
    @DisplayName("testGetUsersServerError()")
    public void testGetUsersServerError() throws Exception {
        when(userService.getAllUsers()).thenThrow(new ServiceException("Internal Server Error"));
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user", HttpMethod.GET);
        result.andDo(print()).andExpect(status().is(400));
    }

    @Test
    @DisplayName("testGetUser()")
    public void testGetUser() throws ClassNotFoundException, InstanceNotFoundException {
        UserDTO expected = this.userListDTOs.get(0);
        when(userService.getUser(anyLong())).thenReturn(expected);
        UserDTO result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToObject(this.mockMvc,
                "/user/" + expected.getId(), HttpMethod.GET, UserDTO.class);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("testGetUserNotFound()")
    public void testGetUserNotFound() throws Exception {
        when(userService.getUser(anyLong())).thenThrow(new InstanceNotFoundException("InstanceNotFoundException"));
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user/9999999" , HttpMethod.GET);
        result.andDo(print()).andExpect(status().is(400));
    }

    @Test
    @DisplayName("testCreateUser()")
    public void testCreateUser() throws Exception {
        UserDTO expected = this.userListDTOs.get(0);
        when(userService.createUser(any())).thenReturn(expected);
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user", HttpMethod.POST, objectMapper.writeValueAsString(expected));
        result.andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("testCreateUserException()")
    public void testCreateUserException() throws Exception {
        UserDTO expected = this.userListDTOs.get(0);
        when(userService.createUser(any())).thenThrow(new RuntimeException("Runtime Exception"));
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user", HttpMethod.POST, objectMapper.writeValueAsString(expected));
        result.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("testUpdateUser()")
    public void testUpdateUser() throws Exception {
        UserDTO expected = this.userListDTOs.get(0);
        when(userService.updateUser(any())).thenReturn(expected);
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user", HttpMethod.PUT, objectMapper.writeValueAsString(expected));
        result.andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("testUpdateUserException()")
    public void testUpdateUserException() throws Exception {
        UserDTO expected = this.userListDTOs.get(0);
        when(userService.updateUser(any())).thenThrow(new InstanceNotFoundException("InstanceNotFoundException"));
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user", HttpMethod.PUT, objectMapper.writeValueAsString(expected));
        result.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("testDeleteUser()")
    public void testDeleteUser() throws Exception {
        UserDTO expected = this.userListDTOs.get(0);
        when(userService.deleteUser(any())).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user/" + expected.getId(), HttpMethod.DELETE, objectMapper.writeValueAsString(expected));
        result.andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("testDeleteUserException()")
    public void testDeleteUserException() throws Exception {
        UserDTO expected = this.userListDTOs.get(0);
        when(userService.deleteUser(any())).thenThrow(new InstanceNotFoundException("InstanceNotFoundException"));
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user/" + expected.getId(), HttpMethod.DELETE, objectMapper.writeValueAsString(expected));
        result.andDo(print()).andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("testGetUsersByFilter()")
    public void testGetUsersByFilter() throws Exception {
        UserFilterResponseDTO response = new UserFilterResponseDTO();
        response.setData(this.userListDTOs);
        response.setNumberOfElements(this.userListDTOs.size());
        when(userService.getUsersByFilter(any())).thenReturn(response);
        ObjectMapper objectMapper = new ObjectMapper();
        FilterQueryDTO filterQueryDTO = new FilterQueryDTO();
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user/filter", HttpMethod.POST, objectMapper.writeValueAsString(filterQueryDTO));
        result.andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("testGetUsersByFilterServiceException()")
    public void testGetUsersByFilterServiceException() throws Exception {
        UserFilterResponseDTO response = new UserFilterResponseDTO();
        response.setData(this.userListDTOs);
        response.setNumberOfElements(this.userListDTOs.size());
        when(userService.getUsersByFilter(any())).thenThrow(new ServiceException("Service Exception"));
        ObjectMapper objectMapper = new ObjectMapper();
        FilterQueryDTO filterQueryDTO = new FilterQueryDTO();
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user/filter", HttpMethod.POST, objectMapper.writeValueAsString(filterQueryDTO));
        result.andDo(print()).andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("testGetUsersByFilterParseException()")
    public void testGetUsersByFilterParseException() throws Exception {
        UserFilterResponseDTO response = new UserFilterResponseDTO();
        response.setData(this.userListDTOs);
        response.setNumberOfElements(this.userListDTOs.size());
        when(userService.getUsersByFilter(any())).thenThrow(new ParseException("Parse Exception", 0));
        ObjectMapper objectMapper = new ObjectMapper();
        FilterQueryDTO filterQueryDTO = new FilterQueryDTO();
        ResultActions result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToAnalyseResult(this.mockMvc,
                "/user/filter", HttpMethod.POST, objectMapper.writeValueAsString(filterQueryDTO));
        result.andDo(print()).andExpect(status().isBadRequest());
    }

}
