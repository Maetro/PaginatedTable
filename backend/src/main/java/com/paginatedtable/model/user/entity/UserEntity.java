package com.paginatedtable.model.user.entity;

import com.paginatedtable.model.role.entity.RoleEntity;
import com.paginatedtable.model.user.entity.enums.UserStatusEnum;
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
@EqualsAndHashCode
public class UserEntity{

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

    @Column(name = "is_male")
    private Boolean isMale;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_role",
            inverseJoinColumns = @JoinColumn(name = "role_id", columnDefinition = "BIGINT"),
            joinColumns = @JoinColumn(name = "user_id", nullable = true, columnDefinition = "BIGINT"))
    @Fetch(FetchMode.SELECT)
    private List<RoleEntity> roles;
}
