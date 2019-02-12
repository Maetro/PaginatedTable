package com.ramoncasares.paginatedtable.controller;

import com.ramoncasares.paginatedtable.service.common.dto.FilterQueryDTO;
import com.ramoncasares.paginatedtable.service.user.dto.UserDTO;
import com.ramoncasares.paginatedtable.service.user.dto.UserFilterResponseDTO;
import com.ramoncasares.paginatedtable.service.user.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private static final Log log = LogFactory.getLog(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        log.debug("getAllUsers()");
        List<UserDTO> users = new ArrayList<>();
        try {
            users = this.userService.getAllUsers();
        } catch (Exception e) {
            log.error("getAuditByFilter", e);
            return new ResponseEntity<>(users, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<UserDTO>> getUser(@PathVariable("userId") final Long userId){
        log.debug("getUser()");
        UserDTO user = null;
        try {
            user = this.userService.getUser(userId);
        } catch (Exception e) {
            log.error("getAuditByFilter", e);
            return new ResponseEntity(user, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO userDTO){
        log.debug("createUser()");
        UserDTO user = null;
        try {
            user = this.userService.createUser(userDTO);
        } catch (Exception e) {
            log.error("getAuditByFilter", e);
            return new ResponseEntity(user, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody final UserDTO userDTO) throws InstanceNotFoundException {
        log.debug("updateUser()");
        UserDTO user = null;
        try {
            user = this.userService.updateUser(userDTO);
        } catch (Exception e) {
            log.error("getAuditByFilter", e);
            return new ResponseEntity(user, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") final Long userId) throws InstanceNotFoundException{
        log.debug("deleteUser()");
        boolean userDeleted = false;
        try {
            userDeleted = this.userService.deleteUser(userId);
        } catch (Exception e) {
            log.error("getAuditByFilter", e);
            return new ResponseEntity(userDeleted, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userDeleted, HttpStatus.OK);
    }

    /**
     * Gets users by filter.
     *
     * @param filterQueryDTO the filter query dto
     * @return the users by filter
     */
    @PostMapping(path = "/filter")
    public ResponseEntity<UserFilterResponseDTO> getUsersByFilter(@RequestBody FilterQueryDTO filterQueryDTO) {
        log.debug("getUsersByFilter()");
        UserFilterResponseDTO users = null;
        try {
            users = userService.getUsersByFilter(filterQueryDTO);
        } catch (ServiceException e) {
            log.error("Could not find users with filter= " + filterQueryDTO, e);
            return new ResponseEntity(users, HttpStatus.BAD_REQUEST);
        } catch (ParseException e) {
            log.error("Parse error= " + filterQueryDTO, e);
            return new ResponseEntity(users, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(users, HttpStatus.OK);
    }

}
