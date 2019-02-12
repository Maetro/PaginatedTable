package com.ramoncasares.paginatedtable.common;

import com.ramoncasares.paginatedtable.model.common.repository.TableFilterRepository;
import com.ramoncasares.paginatedtable.model.role.repository.RoleRepository;
import com.ramoncasares.paginatedtable.model.user.repository.UserRepository;
import com.ramoncasares.paginatedtable.service.user.service.UserService;
import com.ramoncasares.paginatedtable.service.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public abstract class ServiceUnitDataTests {

    @BeforeEach
    public void initializeBeforeEachTestCommon(){
        initializeBeforeEachTest();
    }

    protected abstract void initializeBeforeEachTest();

}
