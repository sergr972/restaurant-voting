package ru.sergr972.restaurantvoting.util;

import ru.sergr972.restaurantvoting.error.IllegalRequestDataException;

import java.time.*;

public class TimeUtil {

    public static final LocalTime END_OF_VOTE = LocalTime.of(11, 0);
    public static Clock clock = Clock.systemDefaultZone();

    public static void checkTime() {
        if (LocalTime.now(clock).isAfter(END_OF_VOTE)) {
            throw new IllegalRequestDataException("Re-voting time ended at 11:00 AM");
        }
    }

    public static void setTime(LocalDate date, LocalTime time) {
        TimeUtil.clock = Clock.fixed(LocalDateTime.of(date, time).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
    }
}
