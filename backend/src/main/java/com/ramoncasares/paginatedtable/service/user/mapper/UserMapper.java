package com.ramoncasares.paginatedtable.service.user.mapper;

import com.ramoncasares.paginatedtable.model.role.entity.RoleEntity;
import com.ramoncasares.paginatedtable.model.user.entity.UserEntity;
import com.ramoncasares.paginatedtable.service.role.dto.RoleDTO;
import com.ramoncasares.paginatedtable.service.role.mapper.RoleMapper;
import com.ramoncasares.paginatedtable.service.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper instance = Mappers.getMapper(UserMapper.class);

    List<UserDTO> userEntityListToUserDTOList(List<UserEntity> users);

    @Mappings({
            @Mapping(source = "roles", target = "roles", qualifiedByName = "roleWithoutUser"),
    })
    UserDTO userEntityToUserDTO(UserEntity eventEntity);

    List<UserEntity> userDTOListToUserEntityList(List<UserDTO> users);

    UserEntity userDTOToUserEntity(UserDTO eventEntity);

    @Named("roleWithoutUser")
    default RoleDTO roleWithOutUser(RoleEntity roleEntity) {
        return RoleMapper.instance.roleEntityToRoleDTO(roleEntity);
    }


}