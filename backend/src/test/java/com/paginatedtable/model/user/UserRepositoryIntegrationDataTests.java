package com.paginatedtable.model.user;

import com.paginatedtable.common.AbstractRepositoryIntegrationDataTests;
import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.model.role.repository.RoleRepository;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.model.user.entity.enums.UserStatusEnum;
import com.paginatedtable.model.user.repository.UserRepository;
import com.paginatedtable.util.PaginatedTableTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("User Repository Unit Tests")
public class UserRepositoryIntegrationDataTests extends AbstractRepositoryIntegrationDataTests {

    private List<RoleEntity> roles;

    protected List<UserEntity> usersList;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void initializeBeforeEachTest() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        this.roles = new ArrayList<>();
        RoleEntity r1 = new RoleEntity();
        r1.setName("Role 1");
        r1 = this.roleRepository.save(r1);
        this.roles.add(r1);

        RoleEntity r2 = new RoleEntity();
        r2.setName("Role 2");
        r2 = this.roleRepository.save(r2);
        this.roles.add(r2);
        this.usersList = new ArrayList<>();
        UserEntity u1 = createTestUserAndSave("Rylan", "Melia", UserStatusEnum.ACTIVE, r1);
        this.usersList.add(u1);
        UserEntity u2 = createTestUserAndSave("Natan", "Reeve", UserStatusEnum.ACTIVE, r1);
        this.usersList.add(u2);
        UserEntity u3 = createTestUserAndSave("Arisha", "Price",  UserStatusEnum.PENDING, r1);
        this.usersList.add(u3);
        UserEntity u4 = createTestUserAndSave("Tiya", "Parker",  UserStatusEnum.ACTIVE, r2);
        this.usersList.add(u4);
        UserEntity u5 = createTestUserAndSave("Evangeline", "Padilla",  UserStatusEnum.DELETED, r2);
        this.usersList.add(u5);
        UserEntity u6 = createTestUserAndSave("Steve", "Parker", UserStatusEnum.PENDING, r2);
        this.usersList.add(u6);
    }

    private UserEntity createTestUserAndSave(String name, String surname, UserStatusEnum userStatus, RoleEntity role) {

        UserEntity user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        user.setUserStatus(userStatus);
        user.setBirthdate(PaginatedTableTestsUtils.getRandomBirthDate());
        user.setNumberOfChildren(ThreadLocalRandom.current().nextInt(0, 2));
        user.setScore(ThreadLocalRandom.current().nextInt(0, 100));
        user.setRoles(Arrays.asList(role));
        UserEntity userSaved = this.userRepository.save(user);
        return userSaved;
    }

    /**
     * Test get all users.
     */
    @Test
    @Transactional
    @DisplayName("getAllUsersTest()")
    public void getAllUsersTest(){
        Iterable<UserEntity> userListBD = this.userRepository.findAll();
        List<UserEntity> users = new ArrayList<>();
        userListBD.forEach(users::add);
        assertEquals(6, users.size());
        assertEquals(usersList, users);
    }

}
