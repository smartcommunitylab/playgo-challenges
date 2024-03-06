package it.smartcommunitylab.challenges.bean;

import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Settings {
    private Date start;
    private Period duration;
    private Set<String> modes;
    private boolean hide;
    private List<Suspension> suspensions;
    private Map<String, Double> modeMax;
    private Map<String, Double> modeMin;

    public Settings(Date start, Period duration) {
        if (start == null) {
            throw new IllegalArgumentException("settings date start is required");
        }

        if (duration == null) {
            throw new IllegalArgumentException("settings duration is required");
        }
        this.start = start;
        this.duration = duration;
        suspensions = new ArrayList<>();
        modes = new HashSet<>();
        modeMax = new HashMap<>();
        modeMin = new HashMap<>();
    }

	public Settings(Date start, Period duration, Map<String, Double> modeMax, Map<String, Double> modeMin) {
		if (start == null) {
			throw new IllegalArgumentException("settings date start is required");
		}

		if (duration == null) {
			throw new IllegalArgumentException("settings duration is required");
		}
		this.start = start;
		this.duration = duration;
		suspensions = new ArrayList<>();
		modes = new HashSet<>();
		this.modeMax = modeMax;
		this.modeMin = modeMin;
	}

	public Set<String> getModes() {
        return modes;
    }

    public void setModes(Set<String> modes) {
        if (modes != null) {
            this.modes = modes;
        }
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public Date getStart() {
        return start;
    }


    public Period getDuration() {
        return duration;
    }

    public List<Suspension> getSuspensions() {
        return suspensions;
    }

    public void setSuspensions(List<Suspension> suspensions) {
        if (suspensions != null) {
            this.suspensions = suspensions;
        }
    }

	public Map<String, Double> getModeMax() {
		return modeMax;
	}

	public void setModeMax(Map<String, Double> modeMax) {
		this.modeMax = modeMax;
	}

	public Map<String, Double> getModeMin() {
		return modeMin;
	}

	public void setModeMin(Map<String, Double> modeMin) {
		this.modeMin = modeMin;
	}
    
}
