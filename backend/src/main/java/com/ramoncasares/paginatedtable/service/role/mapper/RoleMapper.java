package com.ramoncasares.paginatedtable.service.role.mapper;

import com.ramoncasares.paginatedtable.model.role.entity.RoleEntity;
import com.ramoncasares.paginatedtable.service.role.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {

    RoleMapper instance = Mappers.getMapper(RoleMapper.class);

    List<RoleDTO> roleEntityListToRoleDTOList(List<RoleEntity> roles);

    @Mappings({
            @Mapping(target = "users", ignore = true)
    })
    RoleDTO roleEntityToRoleDTO(RoleEntity roleEntity);

    List<RoleEntity> roleDTOListToRoleEntityList(List<RoleDTO> roles);

    RoleEntity roleDTOToRoleEntity(RoleDTO roleDTO);
    
}
