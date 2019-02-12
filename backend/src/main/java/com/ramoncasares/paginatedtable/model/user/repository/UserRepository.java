package com.ramoncasares.paginatedtable.model.user.repository;

import com.ramoncasares.paginatedtable.model.role.entity.RoleEntity;
import com.ramoncasares.paginatedtable.model.user.entity.UserEntity;
import com.ramoncasares.paginatedtable.model.user.entity.enums.UserStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    /**
     * Find all set.
     *
     * @param userStatusEnum the user status enum
     * @return the set
     */
    Page<UserEntity> findAllByUserStatusIs(Pageable pageable, UserStatusEnum userStatusEnum);

    /**
     * Gets all by roles in.
     *
     * @param roles          the roles
     * @param userStatusEnum the user status enum
     * @return the all by roles in
     */
    Set<UserEntity> getAllByRolesInAndUserStatusIs(List<RoleEntity> roles, UserStatusEnum userStatusEnum);


}
