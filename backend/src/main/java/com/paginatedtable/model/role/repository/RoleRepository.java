package com.paginatedtable.model.role.repository;

import com.paginatedtable.model.role.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * The interface Role repository.
 */
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Long> {

    @Override
    public List<RoleEntity> findAll();

    /**
     * Find all by id in set.
     *
     * @param ids the ids
     * @return the set
     */
    public List<RoleEntity> findAllByIdIn(List<Long> ids);


    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    Page<RoleEntity> findAll(Pageable pageable);

}
