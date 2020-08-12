package it.smartcommunitylab.challenges;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import it.smartcommunitylab.challenges.bean.Settings;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;
import it.smartcommunitylab.challenges.bean.Suspension;

public class TimeHelperTest {

    @Test
    public void calculate_next_start() {
        TimeHelper timeHelper = new TimeHelper(new ExecDate("2020-06-11"));
        // timeHelper.setClock(new ExecDateClock(DateHelper.dateFromIso("2020-06-11").toInstant()));
        final Period sevenDaysDuration = Period.ofDays(7);
        final Date startGameDate = DateHelper.dateFromIso("2019-10-26");
        final Date nextStart = timeHelper.calculateNextStart(startGameDate, sevenDaysDuration);
        assertThat(nextStart).isEqualTo("2020-06-13");
    }

    @Test
    public void when_now_is_before_start_then_next_start_is_the_start_date() {
        TimeHelper timeHelper = new TimeHelper(new ExecDate("2020-06-01"));
        // timeHelper.setClock(new ExecDateClock(DateHelper.dateFromIso("2020-06-01").toInstant()));
        final Period twoDaysDuration = Period.ofDays(2);
        final Date startGameDate = DateHelper.dateFromIso("2020-07-01");
        final Date nextStart = timeHelper.calculateNextStart(startGameDate, twoDaysDuration);
        assertThat(nextStart).isEqualTo("2020-07-01");
    }

    @Test
    public void when_now_is_the_start_date_then_next_start_is_the_start_date() {
        TimeHelper timeHelper = new TimeHelper(new ExecDate("2020-07-01"));
        // timeHelper.setClock(new ExecDateClock(DateHelper.dateFromIso("2020-07-01").toInstant()));
        final Period twoDaysDuration = Period.ofDays(2);
        final Date startGameDate = DateHelper.dateFromIso("2020-07-01");
        final Date nextStart = timeHelper.calculateNextStart(startGameDate, twoDaysDuration);
        assertThat(nextStart).isEqualTo("2020-07-01");
    }

    @Test
    public void challenge_of_three_days() {
        TimeHelper timeHelper = new TimeHelper();
        final Period threeDaysDuration = Period.ofDays(3);
        Date endChallengeDate =
                timeHelper.calculateEnd(DateHelper.dateFromIso("2020-06-06"), threeDaysDuration);
        assertThat(endChallengeDate).isEqualTo("2020-06-09");
    }

    @Test
    public void start_date_in_standard_time_and_end_in_dst() {
        TimeHelper timeHelper = new TimeHelper();
        final Period fourDaysDuration = Period.ofDays(4);
        Date endChallengeDate =
                timeHelper.calculateEnd(DateHelper.dateFromIso("2020-03-28"), fourDaysDuration);
        assertThat(endChallengeDate).isEqualTo("2020-04-01");
    }

    @Test
    public void start_date_in_dst_and_end_in_standard_time() {
        TimeHelper timeHelper = new TimeHelper();
        final Period sevenDaysDuration = Period.ofDays(7);
        Date endChallengeDate =
                timeHelper.calculateEnd(DateHelper.dateFromIso("2020-10-20"), sevenDaysDuration);
        assertThat(endChallengeDate).isEqualTo("2020-10-27");
    }

    @Test
    public void assign_challenge_if_suspensions_are_empty() {
        TimeHelper timeHelper = new TimeHelper();
        List<Suspension> emptySuspensions = new ArrayList<>();
        StandardSingleChallenge standardSingleChallenges = new StandardSingleChallenge();
        Settings config = new Settings(DateHelper.dateFromIso("2020-06-20"), Period.ofDays(1));
        config.setSuspensions(emptySuspensions);
        standardSingleChallenges.setSettings(config);
        boolean shouldBeSuspended =
                timeHelper.shouldBeSospended(DateHelper.dateFromIso("2020-06-20"),
                        standardSingleChallenges);
        assertThat(shouldBeSuspended).isFalse();
    }

    @Test
    public void not_assign_challenge_if_startDate_is_contained_into_a_suspension() {
        TimeHelper timeHelper = new TimeHelper();
        List<Suspension> suspensions = new ArrayList<>();
        suspensions.add(new Suspension(DateHelper.dateFromIso("2020-06-19"),
                DateHelper.dateFromIso("2020-06-21")));
        StandardSingleChallenge standardSingleChallenges = new StandardSingleChallenge();
        Settings config = new Settings(DateHelper.dateFromIso("2020-06-20"), Period.ofDays(1));
        config.setSuspensions(suspensions);
        standardSingleChallenges.setSettings(config);
        boolean shouldBeSuspended =
                timeHelper.shouldBeSospended(DateHelper.dateFromIso("2020-06-20"),
                        standardSingleChallenges);
        assertThat(shouldBeSuspended).isTrue();
    }

    @Test
    public void suspension_from_is_inclusive() {
        TimeHelper timeHelper = new TimeHelper();
        List<Suspension> suspensions = new ArrayList<>();
        suspensions.add(new Suspension(DateHelper.dateFromIso("2020-06-20"),
                DateHelper.dateFromIso("2020-06-21")));
        StandardSingleChallenge standardSingleChallenges = new StandardSingleChallenge();
        Settings config = new Settings(DateHelper.dateFromIso("2020-06-20"), Period.ofDays(1));
        config.setSuspensions(suspensions);
        standardSingleChallenges.setSettings(config);
        boolean shouldBeSuspended =
                timeHelper.shouldBeSospended(DateHelper.dateFromIso("2020-06-20"),
                        standardSingleChallenges);
        assertThat(shouldBeSuspended).isTrue();
    }

    @Test
    public void suspension_to_is_exclusive() {
        TimeHelper timeHelper = new TimeHelper();
        List<Suspension> suspensions = new ArrayList<>();
        suspensions.add(new Suspension(DateHelper.dateFromIso("2020-06-19"),
                DateHelper.dateFromIso("2020-06-20")));
        StandardSingleChallenge standardSingleChallenges = new StandardSingleChallenge();
        Settings config = new Settings(DateHelper.dateFromIso("2020-06-20"), Period.ofDays(1));
        config.setSuspensions(suspensions);
        standardSingleChallenges.setSettings(config);
        boolean shouldBeSuspended =
                timeHelper.shouldBeSospended(DateHelper.dateFromIso("2020-06-20"),
                        standardSingleChallenges);
        assertThat(shouldBeSuspended).isFalse();
    }

    @Test
    public void valid_date_between_suspensions() {
        TimeHelper timeHelper = new TimeHelper();
        List<Suspension> suspensions = new ArrayList<>();
        suspensions.add(new Suspension(DateHelper.dateFromIso("2020-06-19"),
                DateHelper.dateFromIso("2020-06-25")));
        suspensions.add(new Suspension(DateHelper.dateFromIso("2020-06-27"),
                DateHelper.dateFromIso("2020-06-30")));
        StandardSingleChallenge standardSingleChallenges = new StandardSingleChallenge();
        Settings config = new Settings(DateHelper.dateFromIso("2020-06-20"), Period.ofDays(1));
        config.setSuspensions(suspensions);
        standardSingleChallenges.setSettings(config);
        boolean shouldBeSuspended =
                timeHelper.shouldBeSospended(DateHelper.dateFromIso("2020-06-26"),
                        standardSingleChallenges);
        assertThat(shouldBeSuspended).isFalse();
    }

    @Test
    public void startDate_after_suspensions() {
        TimeHelper timeHelper = new TimeHelper();
        List<Suspension> suspensions = new ArrayList<>();
        suspensions.add(new Suspension(DateHelper.dateFromIso("2020-06-19"),
                DateHelper.dateFromIso("2020-06-25")));
        suspensions.add(new Suspension(DateHelper.dateFromIso("2020-06-27"),
                DateHelper.dateFromIso("2020-06-30")));
        StandardSingleChallenge standardSingleChallenges = new StandardSingleChallenge();
        Settings config = new Settings(DateHelper.dateFromIso("2020-06-20"), Period.ofDays(1));
        config.setSuspensions(suspensions);
        standardSingleChallenges.setSettings(config);
        boolean shouldBeSuspended =
                timeHelper.shouldBeSospended(DateHelper.dateFromIso("2020-07-02"),
                        standardSingleChallenges);
        assertThat(shouldBeSuspended).isFalse();
    }


    @Test
    public void week_difference_dst() {
        TimeHelper timeHelper = new TimeHelper();
        final int weeks = timeHelper.weekDifference(DateHelper.dateFromIso("2019-10-26"),
                DateHelper.dateFromIso("2019-11-02"));
        assertThat(weeks).isEqualTo(2);
    }

    @Test
    public void week_difference_of_same_date() {
        TimeHelper timeHelper = new TimeHelper();
        final int weeks = timeHelper.weekDifference(DateHelper.dateFromIso("2020-06-20"),
                DateHelper.dateFromIso("2020-06-20"));
        assertThat(weeks).isEqualTo(1);
    }

    @Test
    public void week_difference_next_week() {
        TimeHelper timeHelper = new TimeHelper();
        final int weeks = timeHelper.weekDifference(DateHelper.dateFromIso("2020-06-20"),
                DateHelper.dateFromIso("2020-06-27"));
        assertThat(weeks).isEqualTo(2);
    }

    @Test
    public void week_difference_two_weeks_later() {
        TimeHelper timeHelper = new TimeHelper();
        final int weeks = timeHelper.weekDifference(DateHelper.dateFromIso("2020-06-20"),
                DateHelper.dateFromIso("2020-07-04"));
        assertThat(weeks).isEqualTo(2);
    }
}
