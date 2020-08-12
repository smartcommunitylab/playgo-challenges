package it.smartcommunitylab.challenges;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ExecDateClock implements Clock {
    private Instant now;

    public ExecDateClock(Instant now) {
        this.now = now;
    }

    public ExecDateClock(ExecDate executionDate) {
        this.now = executionDate.getInstant();
    }

    @Override
    public long nowAsMillis() {
        return now.toEpochMilli();
    }

    @Override
    public Date nowAsDate() {
        return Date.from(now);
    }

    @Override
    public LocalDateTime nowAsLocalDateTime() {
        return LocalDateTime.ofInstant(now, ZoneId.systemDefault());
    }

    @Override
    public LocalDate nowAsLocalDate() {
        return LocalDate.from(now);
    }

}
