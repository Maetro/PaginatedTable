package com.ramoncasares.paginatedtable.common;

import com.ramoncasares.paginatedtable.PaginatedTableTestApplication;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=PaginatedTableTestApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractUnitControllerTests {

    @BeforeEach
    public void beforeClass() throws Exception {
        customBeforeClass();
    }


    /**
     * Custom before class.
     *
     * @throws Exception the exception
     */
    protected abstract void customBeforeClass() throws Exception;

}
