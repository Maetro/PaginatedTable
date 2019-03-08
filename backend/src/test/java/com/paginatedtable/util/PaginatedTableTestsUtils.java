package com.paginatedtable.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public final class PaginatedTableTestsUtils {

    private PaginatedTableTestsUtils() {
    }

    public static Date getRandomBirthDate() {

        Calendar startDate = Calendar.getInstance();
        startDate.set(1970, 0, 1);
        return new Date(ThreadLocalRandom.current()
                .nextLong(startDate.getTime().getTime(), Calendar.getInstance().getTime().getTime()));

    }

}
