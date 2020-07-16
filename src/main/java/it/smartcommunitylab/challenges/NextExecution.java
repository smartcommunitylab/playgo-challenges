package it.smartcommunitylab.challenges;

import java.time.Period;
import java.util.Date;

import it.smartcommunitylab.challenges.bean.GroupSettings;
import it.smartcommunitylab.challenges.bean.Settings;
import it.smartcommunitylab.challenges.bean.StandardGroupChallenge;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

class NextExecution {
    private Date start;
    private Date end;
    private Period duration;
    private boolean hidden;
    private int challengeWeek;
    private boolean suspended;

    public NextExecution(StandardSingleChallenge standardSingleChallenge) {
        final TimeHelper timeHelper = new TimeHelper();
        final Settings challengeSettings = standardSingleChallenge.getSettings();
        final Date nextStart = timeHelper.calculateNextStart(challengeSettings.getStart(),
                challengeSettings.getDuration());
        final Date end = timeHelper.calculateEnd(nextStart, challengeSettings.getDuration());
        this.start = nextStart;
        this.end = end;
        this.duration = challengeSettings.getDuration();
        this.hidden = challengeSettings.isHide();
        this.challengeWeek = timeHelper.weekDifference(challengeSettings.getStart(), nextStart);
        this.suspended = timeHelper.shouldBeSospended(nextStart, standardSingleChallenge);
    }

    public NextExecution(StandardGroupChallenge standardGroupChallenge) {
        final TimeHelper timeHelper = new TimeHelper();
        final GroupSettings challengeSettings = standardGroupChallenge.getSettings();
        final Date nextStart = timeHelper.calculateNextStart(challengeSettings.getStart(),
                challengeSettings.getDuration());
        final Date end = timeHelper.calculateEnd(nextStart, challengeSettings.getDuration());
        this.start = nextStart;
        this.end = end;
        this.duration = challengeSettings.getDuration();
        this.hidden = false;
        this.challengeWeek = timeHelper.weekDifference(challengeSettings.getStart(), nextStart);
        this.suspended = false;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Period getDuration() {
        return duration;
    }

    public boolean isHidden() {
        return hidden;
    }

    public int getChallengeWeek() {
        return challengeWeek;
    }

    public boolean isSuspended() {
        return suspended;
    }


}
