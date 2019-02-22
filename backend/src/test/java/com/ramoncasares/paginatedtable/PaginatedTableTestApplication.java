package com.ramoncasares.paginatedtable;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest()
@Configuration
@ComponentScan("com.ramoncasares.paginatedtable")
@TestPropertySource(locations = "classpath:application.properties")
public class PaginatedTableTestApplication {



}
