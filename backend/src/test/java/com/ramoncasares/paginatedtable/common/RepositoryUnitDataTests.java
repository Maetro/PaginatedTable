package com.ramoncasares.paginatedtable.common;

import com.ramoncasares.paginatedtable.PaginatedtableApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RepositoryUnitDataTests {

    @Autowired
    protected TestEntityManager entityManager;

    @BeforeEach
    public void initializeBeforeEachTestCommon(){
        initializeBeforeEachTest();
    }

    protected abstract void initializeBeforeEachTest();

}
