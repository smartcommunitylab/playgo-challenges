package it.smartcommunitylab.challenges;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;
import it.smartcommunitylab.challenges.bean.Suspension;

public class TimeHelper {
    public static final Clock DEFAULT_CLOCK = new SystemClock();
    private Clock clock = DEFAULT_CLOCK;

    private Date dateFrom(LocalDateTime datetime) {
        return Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime localDateTimeFrom(Date date) {
        return new Timestamp(date.getTime()).toLocalDateTime();
    }

    public Date calculateNextStart(Date start, Period duration) {
        final LocalDateTime now = clock.nowAsLocalDateTime();
        LocalDateTime cursorDate = localDateTimeFrom(start);
        while (now.isAfter(cursorDate)) {
            cursorDate = cursorDate.plus(duration);
        }
        return dateFrom(cursorDate);
    }

    public Date calculateEnd(Date start, Period duration) {
        return dateFrom(localDateTimeFrom(start).plus(duration));
    }

    public TimeHelper setClock(Clock clock) {
        if (clock != null) {
            this.clock = clock;
        }
        return this;
    }

    public Clock getClock() {
        return clock;
    }

    public boolean shouldBeSospended(Date startDate,
            StandardSingleChallenge standardSingleChallenges) {
        boolean suspend = false;
        for (Suspension suspension : standardSingleChallenges.getSettings().getSuspensions()) {
            suspend = suspend || suspension.shouldBeSuspended(startDate);
        }
        return suspend;
    }
}
