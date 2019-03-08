package com.paginatedtable.service.user;

import com.paginatedtable.common.AbstractServiceUnitDataTests;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.common.CreateTestMocksHelper;
import com.paginatedtable.model.common.repository.TableFilterRepository;
import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.model.role.repository.RoleRepository;
import com.paginatedtable.model.user.repository.UserRepository;
import com.paginatedtable.service.common.dto.FilterQueryDTO;
import com.paginatedtable.service.common.dto.SortStatus;
import com.paginatedtable.service.user.dto.UserDTO;
import com.paginatedtable.service.user.dto.UserFilterResponseDTO;
import com.paginatedtable.service.user.mapper.UserMapper;
import com.paginatedtable.service.user.service.UserService;
import com.paginatedtable.service.user.service.UserServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("User Service Unit Tests")
public class UserServiceUnitDataTest extends AbstractServiceUnitDataTests {

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
    private List<UserDTO> userListDTOs;

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
        CreateTestMocksHelper.InitUserDataResponse initTestData = CreateTestMocksHelper.instance().initUserData(null, null);
        rolesList = initTestData.getRolesList();
        usersList = initTestData.getUsersList();
        userListDTOs = initTestData.getUserListDTOs();
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

    @Test
    @DisplayName("testFilterQueryDTONull()")
    public void testFilterQueryDTONull() throws ServiceException, ParseException {
        Page<UserEntity> userPage = new PageImpl<>(this.usersList);
        //FilterQueryDto == null;
        FilterQueryDTO query = null;
        List<String> searchQuery = new ArrayList<>();
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertTrue(result.getData().isEmpty());
        assertEquals((Integer) 0, result.getNumberOfElements());
        //searchquery empty && sortstatus null

        //searchquery emply && direction not empty

        //searchquery emply && direction empty
        /*

        //searchquery not emptry
        searchQuery.add("mock");
        query = new FilterQueryDTO(1, 10, new SortStatus("true", "desc"), searchQuery);
        when(techniqueTableFilterRepository.getListOfElementsByFilter(any())).thenReturn(expected);
        result = catalogService.getTechniquesByFilter(query);
        assertEquals((Integer) techniques.size(), result.getNumberOfElements());
        for(Technique technique : techniques) {
            assertTrue(result.getData().contains(technique));
        }*/
    }

    @Test
    @DisplayName("testGetListEmptyOrderAndEmptySearchQuery()")
    public void testGetListEmptyOrderAndEmptySearchQuery() throws ServiceException, ParseException {
        Page<UserEntity> userPage = new PageImpl<>(this.usersList);
        List<String> searchQuery = new ArrayList<>();
        FilterQueryDTO query = new FilterQueryDTO(1, 10, null, searchQuery);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) this.usersList.size(), result.getNumberOfElements());
        for(UserDTO user : this.userListDTOs) {
            assertTrue(result.getData().contains(user));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderDescAndEmptySearchQuery()")
    public void testGetListWithOrderAndEmptySearchQuery() throws ServiceException, ParseException {
        List<String> searchQuery = new ArrayList<>();
        FilterQueryDTO query = new FilterQueryDTO(1, 10, new SortStatus("name", "desc"), searchQuery);
        UserFilterResponseDTO expected = new UserFilterResponseDTO();
        expected.setData(this.userListDTOs);
        expected.setNumberOfElements(this.userListDTOs.size());
        when(userTableFilterRepository.getListOfElementsByFilter(any())).thenReturn(expected);
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) usersList.size(), result.getNumberOfElements());
        this.userListDTOs.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        for(int i = 0; i < this.userListDTOs.size(); i++) {
            assertEquals(this.userListDTOs.get(i), result.getData().get(i));
        }
    }

    @Test
    @DisplayName("testGetListWithEmptyOrderAndEmptySearchQuery()")
    public void testGetListWithEmptyOrderAndEmptySearchQuery() throws ServiceException, ParseException {
        Page<UserEntity> userPage = new PageImpl<>(this.usersList);
        List<String> searchQuery = new ArrayList<>();
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("true", ""), searchQuery);
        UserFilterResponseDTO expected = new UserFilterResponseDTO();
        expected.setData(this.userListDTOs);
        expected.setNumberOfElements(this.userListDTOs.size());
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) usersList.size(), result.getNumberOfElements());
        for(UserDTO user : this.userListDTOs) {
            assertTrue(result.getData().contains(user));
        }
    }

    @Test
    @DisplayName("testGetListWithNameOrderAndGlobalSearchQuery()")
    public void testGetListWithNameOrderAndGlobalSearchQuery() throws ServiceException, ParseException {

        List<String> searchQuery = new ArrayList<>();
        searchQuery.add("Parker");
        FilterQueryDTO query = new FilterQueryDTO(0, 10, new SortStatus("true", ""), searchQuery);
        UserFilterResponseDTO expected = new UserFilterResponseDTO();

        List<UserDTO> expectedList = this.userListDTOs.stream().filter(userDTO ->
                userDTO.getSurname().equals("Parker")).collect(Collectors.toList());
        expectedList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        expected.setData(expectedList);
        expected.setNumberOfElements(expectedList.size());
        when(userTableFilterRepository.getListOfElementsByFilter(any())).thenReturn(expected);
        UserFilterResponseDTO result = userService.getUsersByFilter(query);
        assertEquals((Integer) expectedList.size(), result.getNumberOfElements());

        for(int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), result.getData().get(i));
        }

    }

}
