package it.smartcommunitylab.challenges;

import java.time.Period;
import java.util.Date;
import java.util.Map;

import it.smartcommunitylab.challenges.bean.GroupSettings;
import it.smartcommunitylab.challenges.bean.HighSchoolChallenge;
import it.smartcommunitylab.challenges.bean.Settings;
import it.smartcommunitylab.challenges.bean.SpecialSettings;
import it.smartcommunitylab.challenges.bean.SpecialSingleChallenge;
import it.smartcommunitylab.challenges.bean.StandardGroupChallenge;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

class NextExecution {
    private Date start;
    private Date end;
    private Period duration;
    private boolean hidden;
    private int challengeWeek;
    private boolean suspended;
    private Date executionDate;
    private Map<String, Double> modeMax;
    private Map<String, Double> modeMin;

    public NextExecution(StandardSingleChallenge standardSingleChallenge, ExecDate executionDate) {
        final TimeHelper timeHelper = new TimeHelper(executionDate);
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
        this.executionDate = Date.from(executionDate.getInstant());
        this.modeMax = challengeSettings.getModeMax();
        this.modeMin = challengeSettings.getModeMin();
    }

    public NextExecution(StandardGroupChallenge standardGroupChallenge, ExecDate executionDate) {
        final TimeHelper timeHelper = new TimeHelper(executionDate);
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
        this.executionDate = Date.from(executionDate.getInstant());
        this.modeMax = challengeSettings.getModeMax();
        this.modeMin = challengeSettings.getModeMin();
    }

    public NextExecution(SpecialSingleChallenge specialSingleChallenge, ExecDate executionDate) {
        final TimeHelper timeHelper = new TimeHelper(executionDate);
        final SpecialSettings challengeSettings = specialSingleChallenge.getSettings();
        final Date nextStart = challengeSettings.getStart();
        final Date end = timeHelper.calculateEnd(nextStart, challengeSettings.getDuration());
        this.start = nextStart;
        this.end = end;
        this.duration = challengeSettings.getDuration();
        this.hidden = challengeSettings.isHide();
        this.challengeWeek = timeHelper.weekDifference(challengeSettings.getStart(), nextStart);
        this.suspended = false;
        this.executionDate = Date.from(executionDate.getInstant());
    }
    
    public NextExecution(HighSchoolChallenge hscChallenge, ExecDate executionDate) {
        final TimeHelper timeHelper = new TimeHelper(executionDate);
        final Settings challengeSettings = hscChallenge.getSettings();
        final Date nextStart = timeHelper.calculateNextStart(challengeSettings.getStart(),
                challengeSettings.getDuration());
        final Date end = timeHelper.calculateEnd(nextStart, challengeSettings.getDuration());
        this.start = nextStart;
        this.end = end;
        this.duration = challengeSettings.getDuration();
        this.challengeWeek = timeHelper.weekDifference(challengeSettings.getStart(), nextStart);
        this.executionDate = Date.from(executionDate.getInstant());
        this.modeMax = challengeSettings.getModeMax();
        this.modeMin = challengeSettings.getModeMin();
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

    public Date getExecutionDate() {
        return executionDate;
    }

	public Map<String, Double> getModeMax() {
		return modeMax;
	}

	public Map<String, Double> getModeMin() {
		return modeMin;
	}
	
}
