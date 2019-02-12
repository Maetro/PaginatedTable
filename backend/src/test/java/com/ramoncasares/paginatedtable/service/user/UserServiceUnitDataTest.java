package com.ramoncasares.paginatedtable.service.user;

import com.ramoncasares.paginatedtable.common.ServiceUnitDataTests;
import com.ramoncasares.paginatedtable.model.common.repository.TableFilterRepository;
import com.ramoncasares.paginatedtable.model.role.entity.RoleEntity;
import com.ramoncasares.paginatedtable.model.role.repository.RoleRepository;
import com.ramoncasares.paginatedtable.model.user.entity.UserEntity;
import com.ramoncasares.paginatedtable.model.user.entity.enums.UserStatusEnum;
import com.ramoncasares.paginatedtable.model.user.repository.UserRepository;
import com.ramoncasares.paginatedtable.service.user.dto.UserDTO;
import com.ramoncasares.paginatedtable.service.user.mapper.UserMapper;
import com.ramoncasares.paginatedtable.service.user.service.UserService;
import com.ramoncasares.paginatedtable.service.user.service.UserServiceImpl;
import com.ramoncasares.paginatedtable.util.PaginatedTableTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("User Service Unit Tests")
public class UserServiceUnitDataTest extends ServiceUnitDataTests {

    @TestConfiguration
    static class UserServiceUnitDataTestContextConfiguration {

        @Bean
        public UserService userService(UserRepository userRepository, RoleRepository roleRepository,
                                       TableFilterRepository userTableFilterRepository) {
            return new UserServiceImpl(userRepository, roleRepository, userTableFilterRepository);
        }
    }

    private final Logger logger = LoggerFactory.getLogger(UserServiceUnitDataTest.class);

    private List<RoleEntity> rolesList;

    private List<UserEntity> usersList;

    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private TableFilterRepository userTableFilterRepository;

    @Override
    protected void initializeBeforeEachTest() {
        this.rolesList = new ArrayList<>();
        RoleEntity r1 = new RoleEntity();
        r1.setId(1L);
        r1.setName("Role 1");
        this.rolesList.add(r1);
        RoleEntity r2 = new RoleEntity();
        r2.setId(2L);
        r2.setName("Role 2");
        this.rolesList.add(r2);
        this.usersList = new ArrayList<>();
        UserEntity u1 = createTestUserWithId(1L, "Rylan", "Melia", UserStatusEnum.ACTIVE, r1);
        this.usersList.add(u1);
        UserEntity u2 = createTestUserWithId(2L, "Natan", "Reeve", UserStatusEnum.ACTIVE, r1);
        this.usersList.add(u2);
        UserEntity u3 = createTestUserWithId(3L, "Arisha", "Price", UserStatusEnum.PENDING, r1);
        this.usersList.add(u3);
        UserEntity u4 = createTestUserWithId(4L, "Tiya", "Parker", UserStatusEnum.ACTIVE, r2);
        this.usersList.add(u4);
        UserEntity u5 = createTestUserWithId(5L, "Evangeline", "Padilla", UserStatusEnum.DELETED, r2);
        this.usersList.add(u5);
        UserEntity u6 = createTestUserWithId(6L, "Steve", "Parker", UserStatusEnum.PENDING, r2);
        this.usersList.add(u6);
    }

    private UserEntity createTestUserWithId(long id, String name, String surname, UserStatusEnum userStatus, RoleEntity role) {
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

    @Test
    @DisplayName("testGetAllUsers()")
    public void testGetAllUsers() throws Exception {

        when(userRepository.findAll()).thenReturn(this.usersList);

        List<UserDTO> usersDTOSet = UserMapper.instance.userEntityListToUserDTOList(this.usersList);
        ArrayList<UserDTO> usersDTOList = new ArrayList<>(usersDTOSet);
        List<UserDTO> usersRecovered = this.userService.getAllUsers();
        assertEquals(usersDTOList, usersRecovered);

    }


}
