package com.paginatedtable.service.role;

import com.paginatedtable.common.AbstractServiceUnitDataTests;
import com.paginatedtable.common.CreateTestMocksHelper;
import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.service.role.dto.RoleDTO;
import com.paginatedtable.service.role.mapper.RoleMapper;
import com.paginatedtable.service.user.dto.UserDTO;
import com.paginatedtable.service.user.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Role Mapper Tests")
public class RoleMapperTests extends AbstractServiceUnitDataTests {

    private List<RoleEntity> rolesList;
    private List<RoleDTO> rolesListDTOs;

    @Override
    protected void initializeBeforeEachTest() {
        CreateTestMocksHelper.InitUserDataResponse initTestData = CreateTestMocksHelper.instance().initUserData(null, null);
        rolesList = initTestData.getRolesList();
        rolesListDTOs = initTestData.getRolesListDTOs();
    }

    @Test
    @DisplayName("testRoleEntityListToRoleDTOList()")
    public void testRoleEntityListToRoleDTOList() throws Exception {
        List<RoleDTO> rolesDTOList = RoleMapper.instance.roleEntityListToRoleDTOList(this.rolesList);
        assertEquals(rolesListDTOs, rolesDTOList);
    }

    @Test
    @DisplayName("testNullRoleEntityListToRoleDTOList()")
    public void testNullRoleEntityListToRoleDTOList() throws Exception {
        List<RoleDTO> rolesDTOList = RoleMapper.instance.roleEntityListToRoleDTOList(null);
        assertNull(rolesDTOList);
    }

    @Test
    @DisplayName("testRoleDTOListToRoleEntityList()")
    public void testRoleDTOListToRoleEntityList() throws Exception {
        List<RoleEntity> roleEntityList = RoleMapper.instance.roleDTOListToRoleEntityList(this.rolesListDTOs);
        assertEquals(rolesList, roleEntityList);
    }

    @Test
    @DisplayName("testNullRoleDTOListToRoleEntityList()")
    public void testNullRoleDTOListToRoleEntityList() throws Exception {
        List<RoleEntity> roleEntityList = RoleMapper.instance.roleDTOListToRoleEntityList(null);
        assertNull(roleEntityList);
    }
}
