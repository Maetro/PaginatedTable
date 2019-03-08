package com.paginatedtable.common;

import com.paginatedtable.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
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
