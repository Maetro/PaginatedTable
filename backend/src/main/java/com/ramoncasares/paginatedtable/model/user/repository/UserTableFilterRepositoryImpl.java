package com.ramoncasares.paginatedtable.model.user.repository;

import com.ramoncasares.paginatedtable.model.common.repository.TableFilterRepositoryImpl;
import com.ramoncasares.paginatedtable.model.user.entity.UserEntity;
import com.ramoncasares.paginatedtable.service.common.dto.FilterResponse;
import com.ramoncasares.paginatedtable.service.common.dto.IdentifierFilterDataDTO;
import com.ramoncasares.paginatedtable.service.user.dto.UserDTO;
import com.ramoncasares.paginatedtable.service.user.dto.UserFilterResponseDTO;
import com.ramoncasares.paginatedtable.service.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Qualifier("userTableFilter")
public class UserTableFilterRepositoryImpl extends TableFilterRepositoryImpl<UserEntity, UserDTO, Long> {

    @Override
    protected Collection<UserDTO> adaptEntityListToDTO(List<UserEntity> users) {
        return UserMapper.instance.userEntityListToUserDTOList(users);
    }

    /**
     * Get native query for all fields string.
     *
     * @param completeFilter the complete filter
     * @param sort           the sort
     * @return the string
     */
    @Override
    protected String getNativeQueryForAllFields(String completeFilter, String sort) {
        return "select u.name, u.surname, u.nick, u.email, u.cif, u.company, r.name from cmpd.user u " +
                "inner join cmpd.user_role ur on ur.user_id = u.id " +
                "inner join cmpd.role r on r.id = ur.role_id " +
                "where u.name || u.surname || u.nick || u.email || u.cif || u.company || r.name as rolename like '%"
                + completeFilter + "%'" + sort;
    }

    /**
     * Get new instance of response filter response.
     *
     * @return the filter response
     */
    @Override
    protected FilterResponse getNewInstanceOfResponse() {
        return new UserFilterResponseDTO();
    }

    /**
     * Get entity table class class.
     *
     * @return the class
     */
    @Override
    protected Class getEntityTableClass() {
        return UserEntity.class;
    }

    @Override
    protected IdentifierFilterDataDTO<Long> getEntitiesIdentifiers(List<UserEntity> filteredEntities) {
        IdentifierFilterDataDTO<Long> identifierFilterDataDTO = new IdentifierFilterDataDTO<>();
        identifierFilterDataDTO.setIdentifierFieldName("id");
        List<Long> idList = filteredEntities.stream().map(UserEntity::getId).collect(Collectors.toList());
        identifierFilterDataDTO.setIdentifierFieldFilterList(idList);
        return identifierFilterDataDTO;
    }

}
