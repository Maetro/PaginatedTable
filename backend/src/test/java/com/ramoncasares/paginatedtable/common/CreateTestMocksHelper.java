package com.ramoncasares.paginatedtable.common;

import com.ramoncasares.paginatedtable.model.role.entity.RoleEntity;
import com.ramoncasares.paginatedtable.model.user.entity.UserEntity;
import com.ramoncasares.paginatedtable.model.user.entity.enums.UserStatusEnum;
import com.ramoncasares.paginatedtable.service.user.dto.UserDTO;
import com.ramoncasares.paginatedtable.service.user.mapper.UserMapper;
import com.ramoncasares.paginatedtable.util.PaginatedTableTestsUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class CreateTestMocksHelper {

    private static CreateTestMocksHelper instance;

    private CreateTestMocksHelper() {
    }

    public static CreateTestMocksHelper instance(){
        if (instance == null){
            instance = new CreateTestMocksHelper();
        }
        return instance;
    }

    public InitUserDataResponse initUserData() {
        List<RoleEntity> rolesList = new ArrayList<>();
        RoleEntity r1 = new RoleEntity();
        r1.setId(1L);
        r1.setName("Role 1");
        rolesList.add(r1);
        RoleEntity r2 = new RoleEntity();
        r2.setId(2L);
        r2.setName("Role 2");
        rolesList.add(r2);
        List<UserEntity> usersList = new ArrayList<>();
        UserEntity u1 = createTestUserWithId(1L, "Rylan", "Melia", UserStatusEnum.ACTIVE, r1);
        usersList.add(u1);
        UserEntity u2 = createTestUserWithId(2L, "Natan", "Reeve", UserStatusEnum.ACTIVE, r1);
        usersList.add(u2);
        UserEntity u3 = createTestUserWithId(3L, "Arisha", "Price", UserStatusEnum.PENDING, r1);
        usersList.add(u3);
        UserEntity u4 = createTestUserWithId(4L, "Tiya", "Parker", UserStatusEnum.ACTIVE, r2);
        usersList.add(u4);
        UserEntity u5 = createTestUserWithId(5L, "Evangeline", "Padilla", UserStatusEnum.DELETED, r2);
        usersList.add(u5);
        UserEntity u6 = createTestUserWithId(6L, "Steve", "Parker", UserStatusEnum.PENDING, r2);
        usersList.add(u6);
        List<UserDTO> userListDTOs = UserMapper.instance.userEntityListToUserDTOList(usersList);
        InitUserDataResponse initUserDataResponse = new InitUserDataResponse(rolesList,usersList,userListDTOs);
        return initUserDataResponse;
    }

    private static UserEntity createTestUserWithId(long id, String name, String surname, UserStatusEnum userStatus, RoleEntity role) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setUserStatus(userStatus);
        user.setBirthdate(PaginatedTableTestsUtils.getRandomBirthDate());
        user.setNumberOfChildren(ThreadLocalRandom.current().nextInt(0, 2));
        user.setRoles(Arrays.asList(role));
        user.setScore(ThreadLocalRandom.current().nextInt(0, 100));
        return user;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class InitUserDataResponse {
        List<RoleEntity> rolesList;
        List<UserEntity> usersList;
        List<UserDTO> userListDTOs;

    }

}
