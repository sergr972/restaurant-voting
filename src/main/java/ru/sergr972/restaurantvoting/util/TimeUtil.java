package ru.sergr972.restaurantvoting.util;

import ru.sergr972.restaurantvoting.error.IllegalRequestDataException;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class TimeUtil {

    private static final LocalTime END_OF_VOTE = LocalTime.of(11, 0);
    public static Clock clock = Clock.systemDefaultZone();

    public static void checkTime() {
        if (LocalTime.now(clock).isAfter(END_OF_VOTE)) {
            throw new IllegalRequestDataException("Re-voting time ended at 11:00 AM");
        }
    }

    public static void setTime(String time) {
        TimeUtil.clock = Clock.fixed(Instant.parse(time), ZoneOffset.UTC);
    }
}
