package it.smartcommunitylab.challenges;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ExecDate {
    private Instant instant;

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault());

    public ExecDate() {
        instant = Instant.now();
    }

    public ExecDate(String isoDate) {
        instant = LocalDate.from(DATE_TIME_FORMATTER.parse(isoDate))
                .atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    public Instant getInstant() {
        return instant;
    }

    public String getInstantAsString() {
        return DATE_TIME_FORMATTER.format(instant);

    }
}
