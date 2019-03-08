package com.paginatedtable.common;

import com.paginatedtable.model.role.repository.RoleRepository;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.model.user.repository.UserRepository;
import com.paginatedtable.service.role.dto.RoleDTO;
import com.paginatedtable.service.role.mapper.RoleMapper;
import com.paginatedtable.util.PaginatedTableTestsUtils;
import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.model.user.entity.enums.UserStatusEnum;
import com.paginatedtable.service.user.dto.UserDTO;
import com.paginatedtable.service.user.mapper.UserMapper;
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

    public InitUserDataResponse initUserData(RoleRepository roleRepository, UserRepository userRepository) {
        List<RoleEntity> rolesList = new ArrayList<>();
        RoleEntity r1 = createRoleEntity(roleRepository, rolesList, "Role 1", 1L);
        RoleEntity r2 = createRoleEntity(roleRepository, rolesList, "Role 2", 2L);
        List<UserEntity> usersList = new ArrayList<>();
        createTestUserWithId(userRepository, usersList, 1L, "Rylan", "Melia", UserStatusEnum.ACTIVE, r1, true);
        createTestUserWithId(userRepository, usersList, 2L, "Natan", "Reeve", UserStatusEnum.ACTIVE, r1, true);
        createTestUserWithId(userRepository, usersList, 3L, "Arisha", "Price", UserStatusEnum.PENDING, r1, false);
        createTestUserWithId(userRepository, usersList, 4L, "Tiya", "Parker", UserStatusEnum.ACTIVE, r2, false);
        createTestUserWithId(userRepository, usersList, 5L, "Evangeline", "Padilla", UserStatusEnum.DELETED, r2, false);
        createTestUserWithId(userRepository, usersList, 6L, "Steve", "Parker", UserStatusEnum.PENDING, r2, true);
        List<UserDTO> userListDTOs = UserMapper.instance.userEntityListToUserDTOList(usersList);
        List<RoleDTO> rolesListDTOs = RoleMapper.instance.roleEntityListToRoleDTOList(rolesList);
        InitUserDataResponse initUserDataResponse = new InitUserDataResponse(rolesList, rolesListDTOs, usersList, userListDTOs);
        return initUserDataResponse;
    }

    private RoleEntity createRoleEntity(RoleRepository roleRepository, List<RoleEntity> rolesList, String name, Long id) {
        RoleEntity role = new RoleEntity();
        role.setName(name);
        if (roleRepository != null){
            role = roleRepository.save(role);
        } else {
            role.setId(id);
        }
        rolesList.add(role);
        return role;
    }

    private static UserEntity createTestUserWithId(UserRepository userRepository, List<UserEntity> usersList, long id,
                                                   String name, String surname, UserStatusEnum userStatus,
                                                   RoleEntity role, Boolean isMale) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        user.setUserStatus(userStatus);
        user.setBirthdate(PaginatedTableTestsUtils.getRandomBirthDate());
        user.setNumberOfChildren(ThreadLocalRandom.current().nextInt(0, 2));
        user.setRoles(Arrays.asList(role));
        user.setScore(ThreadLocalRandom.current().nextInt(0, 100));
        user.setIsMale(isMale);
        if (userRepository != null) {
            user = userRepository.save(user);
        } else {
            user.setId(id);
        }
        usersList.add(user);
        return user;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class InitUserDataResponse {
        List<RoleEntity> rolesList;
        List<RoleDTO> rolesListDTOs;
        List<UserEntity> usersList;
        List<UserDTO> userListDTOs;
    }

}
