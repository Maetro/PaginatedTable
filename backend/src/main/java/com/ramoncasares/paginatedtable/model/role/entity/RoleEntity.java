package com.ramoncasares.paginatedtable.model.role.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ramoncasares.paginatedtable.model.common.entity.AuditableEntity;
import com.ramoncasares.paginatedtable.model.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class RoleEntity extends AuditableEntity {

    @Id
    @Column(columnDefinition = "SERIAL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<UserEntity> users;

    private String name;


}
