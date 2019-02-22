package com.ramoncasares.paginatedtable.controller;

import com.ramoncasares.paginatedtable.common.AbstractUnitControllerTests;
import com.ramoncasares.paginatedtable.common.CreateTestMocksHelper;
import com.ramoncasares.paginatedtable.common.PaginatedTableControllerTestHelper;
import com.ramoncasares.paginatedtable.model.role.entity.RoleEntity;
import com.ramoncasares.paginatedtable.model.user.entity.UserEntity;
import com.ramoncasares.paginatedtable.service.user.dto.UserDTO;
import com.ramoncasares.paginatedtable.service.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DisplayName("User Controller Unit Tests")
@WebMvcTest(controllers = UserController.class)
public class UserControllerUnitDataTests extends AbstractUnitControllerTests {

    private final Logger logger = LoggerFactory.getLogger(UserControllerUnitDataTests.class);

    private List<RoleEntity> rolesList;

    private List<UserEntity> usersList;
    private List<UserDTO> userListDTOs;

    /**
     * The Flowable management service.
     */
    @MockBean
    UserService userService;

    @Autowired
    protected MockMvc mockMvc;

    @Override
    protected void customBeforeClass() throws Exception {
        CreateTestMocksHelper.InitUserDataResponse initTestData = CreateTestMocksHelper.instance().initUserData();
        rolesList = initTestData.getRolesList();
        usersList = initTestData.getUsersList();
        userListDTOs = initTestData.getUserListDTOs();
    }

    @Test
    @DisplayName("testGetUsers()")
    public void testGetUsers() throws ClassNotFoundException {
        List<UserDTO> expected = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(this.userListDTOs);
        List<UserDTO> result = PaginatedTableControllerTestHelper.tryHTTPRequestWithUserTypeToObjectList(this.mockMvc,
                "/user", HttpMethod.GET, UserDTO.class);
        for(UserDTO user : this.userListDTOs) {
            assertTrue(result.contains(user));
        }
    }

}
