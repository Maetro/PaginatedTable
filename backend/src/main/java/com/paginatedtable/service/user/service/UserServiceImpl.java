package com.paginatedtable.service.user.service;

import com.paginatedtable.model.common.repository.TableFilterRepository;
import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.model.role.repository.RoleRepository;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.model.user.repository.UserRepository;
import com.paginatedtable.service.common.dto.FilterQueryDTO;
import com.paginatedtable.service.role.dto.RoleDTO;
import com.paginatedtable.service.user.dto.UserDTO;
import com.paginatedtable.service.user.dto.UserFilterResponseDTO;
import com.paginatedtable.service.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.management.InstanceNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final TableFilterRepository userTableFilterRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           @Qualifier("userTableFilter") TableFilterRepository userTableFilterRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userTableFilterRepository = userTableFilterRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        Iterable<UserEntity> usersIterable = this.userRepository.findAll();
        List<UserEntity> users = new ArrayList<>();
        usersIterable.forEach(users::add);
        return UserMapper.instance.userEntityListToUserDTOList(users);
    }

    @Override
    public UserDTO getUser(Long userId) throws InstanceNotFoundException {
        UserEntity userFound = this.userRepository.findById(userId).orElseThrow(() ->
                new InstanceNotFoundException("User not found: " + userId));
        return UserMapper.instance.userEntityToUserDTO(userFound);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = UserMapper.instance.userDTOToUserEntity(userDTO);
        UserEntity userSaved = this.userRepository.save(userEntity);
        return UserMapper.instance.userEntityToUserDTO(userSaved);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) throws InstanceNotFoundException {
        UserEntity userToUpdate = this.userRepository.findById(userDTO.getId()).orElseThrow(() ->
                new InstanceNotFoundException("User not found: " + userDTO.getId()));
        userToUpdate.setName(userDTO.getName());
        userToUpdate.setSurname(userDTO.getSurname());
        userToUpdate.setBirthdate(userDTO.getBirthdate());
        userToUpdate.setScore(userDTO.getScore());
        userToUpdate.setNumberOfChildren(userDTO.getNumberOfChildren());
        userToUpdate.setUserStatus(userDTO.getUserStatus());
        userToUpdate.setScore(userDTO.getScore());
        if (!CollectionUtils.isEmpty(userDTO.getRoles())){
            List<Long> roleIds = userDTO.getRoles().stream().map(RoleDTO::getId).collect(Collectors.toList());
            List<RoleEntity> roles = this.roleRepository.findAllByIdIn(roleIds);
            userToUpdate.setRoles(roles);
        } else {
            userToUpdate.setRoles(new ArrayList<>());
        }
        UserEntity userSaved = this.userRepository.save(userToUpdate);
        return UserMapper.instance.userEntityToUserDTO(userSaved);
    }

    @Override
    public boolean deleteUser(Long userId) throws InstanceNotFoundException {
        UserEntity userToDelete = this.userRepository.findById(userId).orElseThrow(() ->
                new InstanceNotFoundException("User not found: " + userId));
        this.userRepository.deleteById(userToDelete.getId());
        return true;
    }

    @Override
    public UserFilterResponseDTO getUsersByFilter(FilterQueryDTO filterQueryDTO) throws ParseException {
        UserFilterResponseDTO response = new UserFilterResponseDTO();
        if (filterQueryDTO != null) {
            if (!CollectionUtils.isEmpty(filterQueryDTO.getSearchQuery())) {
                response = (UserFilterResponseDTO) this.userTableFilterRepository.getListOfElementsByFilter(filterQueryDTO);
            } else {
                if (filterQueryDTO.getSortStatus() != null && StringUtils.isNotBlank(
                        filterQueryDTO.getSortStatus().getDirection())) {
                    response = (UserFilterResponseDTO) this.userTableFilterRepository.getListOfElementsByFilter(filterQueryDTO);
                } else {
                    Page<UserEntity> users = userRepository.findAll(PageRequest.of(filterQueryDTO.getPage(),
                            filterQueryDTO.getNumberOfElements()));
                    response.getData().addAll(UserMapper.instance.userEntityListToUserDTOList(users.getContent()));
                    response.setNumberOfElements((int) users.getTotalElements());
                }
            }
        }
        return response;
    }
}
