package com.paginatedtable.service.role.mapper;

import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.service.role.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {

    RoleMapper instance = Mappers.getMapper(RoleMapper.class);

    List<RoleDTO> roleEntityListToRoleDTOList(List<RoleEntity> roles);

    RoleDTO roleEntityToRoleDTO(RoleEntity roleEntity);

    List<RoleEntity> roleDTOListToRoleEntityList(List<RoleDTO> roles);

    RoleEntity roleDTOToRoleEntity(RoleDTO roleDTO);
    
}
