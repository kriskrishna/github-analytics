package org.springframework.github;

import java.util.Random;
import java.util.UUID;

public final class TestUtil {

    private static Random r = new Random();

    public static String dummyString() {
        return UUID.randomUUID().toString();
    }

    public static Long dummyLong() {
        return r.nextLong();
    }

}
