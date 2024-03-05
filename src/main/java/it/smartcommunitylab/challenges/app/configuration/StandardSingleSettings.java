package it.smartcommunitylab.challenges.app.configuration;

import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.smartcommunitylab.challenges.bean.Settings;

public class StandardSingleSettings {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
            timezone = "Europe/Rome")
    private Date start;
    private Period duration;
    private List<String> modeConcepts;
    private boolean hide;
    private List<SuspensionConfig> suspensions;
    private Map<String, Integer> modeMax;
    private Map<String, Integer> modeMin;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Period getDuration() {
        return duration;
    }

    public void setDuration(Period duration) {
        this.duration = duration;
    }

    public List<String> getModeConcepts() {
        return modeConcepts;
    }

    public void setModeConcepts(List<String> modeConcepts) {
        this.modeConcepts = modeConcepts;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public Map<String, Integer> getModeMax() {
		return modeMax;
	}

	public void setModeMax(Map<String, Integer> modeMax) {
		this.modeMax = modeMax;
	}

	public Map<String, Integer> getModeMin() {
		return modeMin;
	}

	public void setModeMin(Map<String, Integer> modeMin) {
		this.modeMin = modeMin;
	}

	public Settings toConfig() {
        Settings config = new Settings(start, duration, modeMax, modeMin);
        config.setHide(hide);
        config.setModes(new HashSet<>(modeConcepts));
        config.setSuspensions(
                suspensions.stream().map(s -> s.toSuspension()).collect(Collectors.toList()));
        return config;
    }

    public List<SuspensionConfig> getSuspensions() {
        return suspensions;
    }

    public void setSuspensions(List<SuspensionConfig> suspensions) {
        this.suspensions = suspensions;
    }
}
