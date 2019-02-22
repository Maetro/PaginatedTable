package com.ramoncasares.paginatedtable.service.role.dto;

import com.ramoncasares.paginatedtable.model.user.entity.UserEntity;
import com.ramoncasares.paginatedtable.service.user.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RoleDTO implements Serializable {

    private Long id;
    private List<UserDTO> users;
    private String name;

}
