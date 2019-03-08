package com.paginatedtable.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public abstract class AbstractServiceUnitDataTests {

    @BeforeEach
    public void initializeBeforeEachTestCommon(){
        initializeBeforeEachTest();
    }

    protected abstract void initializeBeforeEachTest();

}
