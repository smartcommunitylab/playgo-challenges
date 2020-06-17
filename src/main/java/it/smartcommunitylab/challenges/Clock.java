package it.smartcommunitylab.challenges;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface Clock {
    long nowAsMillis();
    Date nowAsDate();
    LocalDateTime nowAsLocalDateTime();
    LocalDate nowAsLocalDate();
}
