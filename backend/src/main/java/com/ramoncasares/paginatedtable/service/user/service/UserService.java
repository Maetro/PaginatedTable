package com.ramoncasares.paginatedtable.service.user.service;

import com.ramoncasares.paginatedtable.service.common.dto.FilterQueryDTO;
import com.ramoncasares.paginatedtable.service.user.dto.UserDTO;
import com.ramoncasares.paginatedtable.service.user.dto.UserFilterResponseDTO;
import org.hibernate.service.spi.ServiceException;

import javax.management.InstanceNotFoundException;
import java.text.ParseException;
import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Gets all users.
     *
     * @return the all users
     * @throws ServiceException the service exception
     */
    List<UserDTO> getAllUsers() throws ServiceException;


    /**
     * Gets user.
     *
     * @param userId the user id
     * @return the user
     */
    UserDTO getUser(Long userId) throws InstanceNotFoundException;


    /**
     * Create user list.
     *
     * @param userDTO the user dto
     * @return the list
     */
    UserDTO createUser(UserDTO userDTO);

    /**
     * Update user list.
     *
     * @param userDTO the user dto
     * @return the list
     * @throws InstanceNotFoundException the instance not found exception
     */
    UserDTO updateUser(UserDTO userDTO) throws InstanceNotFoundException;

    /**
     * Delete user list.
     *
     * @param userId the user id
     * @return the list
     * @throws InstanceNotFoundException the instance not found exception
     */
    boolean deleteUser(Long userId) throws InstanceNotFoundException;

    /**
     * Gets users by filter.
     *
     * @param userSearchParameters the user search parameters
     * @return the users by filter
     * @throws ServiceException the service exception
     * @throws ParseException   the parse exception
     */
    UserFilterResponseDTO getUsersByFilter(FilterQueryDTO userSearchParameters) throws ServiceException, ParseException;


}
