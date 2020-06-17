package it.smartcommunitylab.challenges;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class SystemClock implements Clock {

    @Override
    public long nowAsMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public Date nowAsDate() {
        return new Date();
    }

    @Override
    public LocalDateTime nowAsLocalDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDate nowAsLocalDate() {
        return LocalDate.now();
    }

}
