package com.ramoncasares.paginatedtable.model.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ramoncasares.paginatedtable.model.common.entity.AuditableEntity;
import com.ramoncasares.paginatedtable.model.role.entity.RoleEntity;
import com.ramoncasares.paginatedtable.model.user.entity.enums.UserStatusEnum;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class UserEntity extends AuditableEntity {

    @Id
    @Column(columnDefinition = "SERIAL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatusEnum userStatus = UserStatusEnum.ACTIVE;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "number_of_children")
    private Integer numberOfChildren;

    @Column(name = "score")
    private Integer score;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_role", inverseJoinColumns = @JoinColumn(name = "role_id", columnDefinition = "BIGINT"),
            joinColumns = @JoinColumn(name = "user_id", nullable = true, columnDefinition = "BIGINT"))
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private List<RoleEntity> roles;
}
