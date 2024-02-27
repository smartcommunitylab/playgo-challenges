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
    private Map<String, Integer> modeMax;

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
    }

    
    public Settings(Date start, Period duration, Map<String, Integer> modeMax) {
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

	public Map<String, Integer> getModeMax() {
		return modeMax;
	}

	public void setModeMax(Map<String, Integer> modeMax) {
		this.modeMax = modeMax;
	}
    
    


}
