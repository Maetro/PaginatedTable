package com.paginatedtable.service.user;

import com.paginatedtable.common.CreateTestMocksHelper;
import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.model.role.repository.RoleRepository;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.model.user.entity.enums.UserStatusEnum;
import com.paginatedtable.model.user.repository.UserRepository;
import com.paginatedtable.service.common.dto.FilterQueryDTO;
import com.paginatedtable.service.common.dto.SortStatus;
import com.paginatedtable.service.role.dto.RoleDTO;
import com.paginatedtable.service.role.mapper.RoleMapper;
import com.paginatedtable.service.user.dto.UserDTO;
import com.paginatedtable.service.user.dto.UserFilterResponseDTO;
import com.paginatedtable.service.user.mapper.UserMapper;
import com.paginatedtable.service.user.service.UserService;
import com.paginatedtable.util.PaginatedTableTestsUtils;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceIntegrationDataTest{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    List<RoleEntity> roles = new ArrayList<>();

    private CreateTestMocksHelper.InitUserDataResponse initUserDataResponse;

    @BeforeEach
    protected void initializeBeforeEachTest() {
        initUserDataResponse = CreateTestMocksHelper.instance().initUserData(
                roleRepository, userRepository);

    }

    @Test
    public void getAllUsersTest() {
        List<UserDTO> expected = UserMapper.instance.userEntityListToUserDTOList(initUserDataResponse.getUsersList());
        List<UserDTO> users = userService.getAllUsers();
        assertEquals(6, users.size());
        assertEquals(expected, users);
    }

    @Test
    public void getUserTest() throws InstanceNotFoundException {
        UserDTO expected = UserMapper.instance.userEntityToUserDTO(
                initUserDataResponse.getUsersList().stream().filter(userEntity -> userEntity.getName().equals("Rylan")).findFirst().get());
        UserDTO user = userService.getUser(expected.getId());
        assertEquals(expected, user);
    }

    @Test
    public void getUserTestNotFound(){
        UserDTO expected = UserMapper.instance.userEntityToUserDTO(
                initUserDataResponse.getUsersList().stream().filter(userEntity -> userEntity.getName().equals("Rylan")).findFirst().get());
        assertThrows(InstanceNotFoundException.class, () -> userService.getUser(-1L));
    }

    @Test
    public void createUserTest(){
        UserDTO user = new UserDTO();
        user.setName("Name");
        user.setSurname("surname");
        user.setUserStatus(UserStatusEnum.ACTIVE);
        user.setBirthdate(PaginatedTableTestsUtils.getRandomBirthDate());
        user.setNumberOfChildren(ThreadLocalRandom.current().nextInt(0, 2));
        user.setScore(ThreadLocalRandom.current().nextInt(0, 100));
        UserDTO userCreated = userService.createUser(user);
        assertEquals("Name", userCreated.getName());
        assertNotNull(userCreated.getId());
    }

    @Test
    public void updateUserTest() throws InstanceNotFoundException {
        UserDTO user = new UserDTO();
        user.setName("Name");
        user.setSurname("surname");
        user.setUserStatus(UserStatusEnum.ACTIVE);
        user.setBirthdate(PaginatedTableTestsUtils.getRandomBirthDate());
        user.setNumberOfChildren(ThreadLocalRandom.current().nextInt(0, 2));
        user.setScore(ThreadLocalRandom.current().nextInt(0, 100));
        UserDTO userCreated = userService.createUser(user);
        RoleDTO roleDTO = RoleMapper.instance.roleEntityToRoleDTO(initUserDataResponse.getRolesList().get(0));
        userCreated.setRoles(Arrays.asList(roleDTO));
        userCreated.setName("Modified");
        UserDTO userModified = userService.updateUser(userCreated);
        assertEquals("Modified", userModified.getName());
        assertNotNull(userModified.getId());
        UserDTO userRecovered = userService.getUser(userCreated.getId());
        assertEquals(userRecovered, userModified);
        userRecovered.setRoles(new ArrayList<>());
        userModified = userService.updateUser(userRecovered);
        assertEquals(0, userModified.getRoles().size());
    }

    @Test
    public void updateUserTestNotFound() throws InstanceNotFoundException {
        UserDTO user = new UserDTO();
        user.setId(-1L);
        assertThrows(InstanceNotFoundException.class, () -> userService.updateUser(user));
    }

    @Test
    public void deleteUserTest() throws InstanceNotFoundException {

        UserEntity userToDelete = initUserDataResponse.getUsersList().get(0);

        UserDTO userRecovered = userService.getUser(userToDelete.getId());
        userService.deleteUser(userToDelete.getId());
        List<UserDTO> users = userService.getAllUsers();
        assertEquals(5, users.size());
    }

    @Test
    public void deleteUserTestNotFound() {
        assertThrows(InstanceNotFoundException.class, () -> userService.deleteUser(-1L));
    }

    @Test
    public void testFilterQueryDTONull() throws ServiceException, ParseException {
        FilterQueryDTO query = null;
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertTrue(result.getData().isEmpty());
        assertEquals((Integer) 0, result.getNumberOfElements());
    }

    @Test
    @DisplayName("testGetListEmptyOrderAndEmptySearchQuery()")
    public void testGetListEmptyOrderAndEmptySearchQuery() throws ServiceException, ParseException {
        List<UserEntity> usersList = initUserDataResponse.getUsersList();
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        FilterQueryDTO query = new FilterQueryDTO(0, 10, null, searchQuery);
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) usersList.size(), result.getNumberOfElements());
        for(UserDTO user : userListDTOs) {
            assertTrue(result.getData().contains(user));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderDescAndEmptySearchQuery()")
    public void testGetListWithOrderAndEmptySearchQuery() throws ServiceException, ParseException {
        List<UserEntity> usersList = initUserDataResponse.getUsersList();
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();

        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) usersList.size(), result.getNumberOfElements());
        userListDTOs.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        for(int i = 0; i < userListDTOs.size(); i++) {
            assertEquals(userListDTOs.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAscAndEmptySearchQuery()")
    public void testGetListWithNameOrderAscAndEmptySearchQuery() throws ServiceException, ParseException {
        List<UserEntity> usersList = initUserDataResponse.getUsersList();
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();

        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "asc"), searchQuery);
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) usersList.size(), result.getNumberOfElements());
        userListDTOs.sort(Comparator.comparing(UserDTO::getName));
        for(int i = 0; i < userListDTOs.size(); i++) {
            assertEquals(userListDTOs.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithEmptyOrderAndEmptySearchQuery()")
    public void testGetListWithEmptyOrderAndEmptySearchQuery() throws ServiceException, ParseException {
        List<UserEntity> usersList = initUserDataResponse.getUsersList();
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) usersList.size(), result.getNumberOfElements());
        for(UserDTO user : userListDTOs) {
            assertTrue(result.getData().contains(user));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndGlobalSearchQuery()")
    public void testGetListWithNameOrderAndGlobalSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("Parker");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getSurname().equals("Parker")).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }

    }

    @Test
    @DisplayName("testGetListWithNameOrderAndStringSearchQuery()")
    public void testGetListWithNameOrderAndStringSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("surname:Parker");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getSurname().equals("Parker")).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }

    }

    @Test
    @DisplayName("testGetListWithNameOrderAndStringEqualSearchQuery()")
    public void testGetListWithNameOrderAndStringEqualSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("surname:==Parker");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getSurname().equals("Parker")).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }

        query.getSearchQuery().clear();
        searchQuery.add("surname:==Park");
        query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);
        result = userService.getUsersByFilter(query);
        assertEquals(new Integer(0), result.getNumberOfElements());
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndStringEqualSearchQuery()")
    public void testGetListWithNameOrderAndIntegerGreaterSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("score:>50");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getScore() > 50).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndIntegerGreaterOrEqualSearchQuery()")
    public void testGetListWithNameOrderAndIntegerGreaterOrEqualSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("score:>=50");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getScore() >= 50).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndIntegerGreaterOrEqualSearchQuery()")
    public void testGetListWithNameOrderAndIntegerLessSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("score:<50");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getScore() < 50).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndIntegerLessOrEqualSearchQuery()")
    public void testGetListWithNameOrderAndIntegerLessOrEqualSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("score:<=50");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getScore() <= 50).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndIntegerLessOrEqualSearchQuery()")
    public void testGetListWithNameOrderAndIntegerRangeSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("score:25..75");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getScore() >= 25 && userDTO.getScore() <= 75).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndBooleanSearchQuery()")
    public void testGetListWithNameOrderAndBooleanSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("isMale:false");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);
        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                !userDTO.getIsMale()).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));

        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndLongSearchQuery()")
    public void testGetListWithNameOrderAndLongSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        UserDTO userDTO = userListDTOs.get(0);
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("id:" + userDTO.getId());
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);
        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTOExpected ->
                userDTOExpected.getId().equals(userDTO.getId())).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));

        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndDateSearchQuery()")
    public void testGetListWithNameOrderAndDateSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        UserDTO userDTO = userListDTOs.get(0);
        Date birthDateToFind = userDTO.getBirthdate();
        List<String> searchQuery = new ArrayList<>();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        searchQuery.add("birthdate:" + dt.format(birthDateToFind));
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        System.out.println(dt.format(birthDateToFind));
        System.out.println(userDTO.getBirthdate());
        assertTrue(result.getData().contains(userDTO));
        query.getSearchQuery().clear();
        dt = new SimpleDateFormat("dd/MM/yyyy");
        query.setSearchQuery(Arrays.asList("birthdate:" + dt.format(birthDateToFind)));
        result = userService.getUsersByFilter(query);
        assertTrue(result.getData().contains(userDTO));
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndEnumSearchQuery()")
    public void testGetListWithNameOrderAndEnumSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("userStatus:ACTIVE");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);
        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                UserStatusEnum.ACTIVE.equals(userDTO.getUserStatus())).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));

        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }

    }

    @Test
    @DisplayName("testGetListWithNameOrderAndMultiFilterSearchQuery()")
    public void testGetListWithNameOrderAndMultiFilterSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("surname:==Parker");
        searchQuery.add("name:==Tiya");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getSurname().equals("Parker") && userDTO.getName().equals("Tiya")).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }

        query.getSearchQuery().clear();
        searchQuery.add("surname:==Park");
        query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);
        result = userService.getUsersByFilter(query);
        assertEquals(new Integer(0), result.getNumberOfElements());

    }

    @Test
    @DisplayName("testGetListWithNameOrderAndRelatedTableSearchQuery()")
    public void testGetListWithNameOrderAndRelatedTableSearchQuery() throws ServiceException, ParseException {
        List<UserDTO> userListDTOs = initUserDataResponse.getUserListDTOs();
        RoleDTO roleToFind = RoleMapper.instance.roleEntityToRoleDTO(initUserDataResponse.getRolesList().stream().filter(
                roleEntity -> roleEntity.getName().equals("Role 1")).findFirst().get());
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("roles.name:Role 1");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("name", "desc"), searchQuery);

        List<UserDTO> expectedList = userListDTOs.stream().filter(userDTO ->
                userDTO.getRoles().contains(roleToFind)).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }

    }


}
