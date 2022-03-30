package com.core.javaflix;

import java.time.Duration;
import java.util.Date;

public class test {
    public static void main(String[] args)
    {
        Date current = new Date(System.currentTimeMillis());
        current.setTime(current.getTime() - Duration.ofDays(90).toMillis());
    }
}
