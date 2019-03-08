package com.paginatedtable.service.role.dto;

import com.paginatedtable.service.user.dto.UserDTO;
import com.paginatedtable.model.user.entity.UserEntity;
import com.paginatedtable.service.user.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class RoleDTO implements Serializable {

    private Long id;
    private String name;

}
