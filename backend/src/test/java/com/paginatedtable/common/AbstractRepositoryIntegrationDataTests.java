package com.paginatedtable.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public abstract class AbstractRepositoryIntegrationDataTests {

    @Autowired
    protected TestEntityManager entityManager;

    @BeforeEach
    public void initializeBeforeEachTestCommon(){
        initializeBeforeEachTest();
    }

    protected abstract void initializeBeforeEachTest();

}
