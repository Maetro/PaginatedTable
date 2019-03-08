package com.paginatedtable.service.user.dto;

import com.paginatedtable.model.user.entity.enums.UserStatusEnum;
import com.paginatedtable.service.role.dto.RoleDTO;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO implements Serializable {

    private Long id;
    private String name;
    private String surname;
    private UserStatusEnum userStatus;
    private Date birthdate;
    private Integer numberOfChildren;
    private Integer score;
    private List<RoleDTO> roles;
    private Boolean isMale;

}
