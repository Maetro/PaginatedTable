package com.paginatedtable.model.user.repository;

import com.paginatedtable.model.common.repository.TableFilterRepositoryImpl;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.service.common.dto.IdentifierFilterDataDTO;
import com.paginatedtable.service.common.dto.FilterResponse;
import com.paginatedtable.service.user.dto.UserDTO;
import com.paginatedtable.service.user.dto.UserFilterResponseDTO;
import com.paginatedtable.service.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
     *
     * @param entityManager
     * @param completeFilter the complete filter
     * @param sort           the sort
     * @return the string
     */
    @Override
    protected Query getNativeQueryForAllFields(EntityManager entityManager, String completeFilter, String sort) {
        return entityManager.createNativeQuery(" select u.id, u.name, u.surname, u.user_status, u.birthdate, u.number_of_children, u.score from user u " +
                "inner join user_role ur on ur.user_id = u.id " +
                "inner join role r on r.id = ur.role_id " +
                "where u.id || u.name || u.surname || u.user_status " + // || to_char(u.birthdate, 'DD/MM/YYYY HH24:MI:SS') " +
                "|| u.number_of_children || u.score || r.name like '%"
                + completeFilter + "%'" + sort, UserEntity.class);
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
