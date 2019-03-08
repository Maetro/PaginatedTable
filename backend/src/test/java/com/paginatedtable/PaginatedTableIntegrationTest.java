package com.paginatedtable;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PaginatedtableApplication.class)
public class PaginatedTableIntegrationTest {

    @Test
    public void contextLoads() {
        System.out.println("contextLoads");
    }

    @BeforeEach
    public void initializeBeforeEachTestCommon(){
    }


}
