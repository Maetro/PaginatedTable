package com.paginatedtable.service.user;

import com.paginatedtable.common.AbstractServiceUnitDataTests;
import com.paginatedtable.common.CreateTestMocksHelper;
import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.service.user.dto.UserDTO;
import com.paginatedtable.service.user.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@DisplayName("User Mapper Tests")
public class UserMapperTests extends AbstractServiceUnitDataTests {

    private List<UserEntity> usersList;
    private List<UserDTO> userListDTOs;

    @Override
    protected void initializeBeforeEachTest() {
        CreateTestMocksHelper.InitUserDataResponse initTestData = CreateTestMocksHelper.instance().initUserData(null, null);
        usersList = initTestData.getUsersList();
        userListDTOs = initTestData.getUserListDTOs();
    }

    @Test
    @DisplayName("testUserEntityListToUserDTOList()")
    public void testUserEntityListToUserDTOList() throws Exception {
        List<UserDTO> usersDTOList = UserMapper.instance.userEntityListToUserDTOList(this.usersList);
        assertEquals(userListDTOs, usersDTOList);
    }

    @Test
    @DisplayName("testNullUserEntityListToUserDTOList()")
    public void testNullUserEntityListToUserDTOList() throws Exception {
        List<UserDTO> usersDTOList = UserMapper.instance.userEntityListToUserDTOList(null);
        assertNull(usersDTOList);
    }

    @Test
    @DisplayName("testUserDTOListToUserEntityList()")
    public void testUserDTOListToUserEntityList() throws Exception {
        List<UserEntity> usersEntityList = UserMapper.instance.userDTOListToUserEntityList(this.userListDTOs);
        assertEquals(usersEntityList, usersList);
    }

    @Test
    @DisplayName("testNullUserDTOListToUserEntityList()")
    public void testNullUserDTOListToUserEntityList() throws Exception {
        List<UserEntity> usersEntityList = UserMapper.instance.userDTOListToUserEntityList(null);
        assertNull(usersEntityList);
    }

}
