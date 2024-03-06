package it.smartcommunitylab.challenges.bean;

import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GroupSettings {
	private Date start;
	private Period duration;
	private String model;
	private Set<String> modes;
	private int minLevel;
	private Map<String, Double> modeMax;
	private Map<String, Double> modeMin;

	public GroupSettings(Date start, Period duration) {
		if (start == null) {
			throw new IllegalArgumentException("settings date start is required");
		}

		if (duration == null) {
			throw new IllegalArgumentException("settings duration is required");
		}
		this.start = start;
		this.duration = duration;
		modes = new HashSet<>();
	}
	
	public GroupSettings(Date start, Period duration, Map<String, Double> modeMax, Map<String, Double> modeMin) {
		if (start == null) {
			throw new IllegalArgumentException("settings date start is required");
		}

		if (duration == null) {
			throw new IllegalArgumentException("settings duration is required");
		}
		this.start = start;
		this.duration = duration;
		modes = new HashSet<>();
		this.modeMax = modeMax;
		this.modeMin = modeMin;
	}

	public Date getStart() {
		return start;
	}

	public Period getDuration() {
		return duration;
	}

	public Set<String> getModes() {
		return modes;
	}

	public void setModes(Set<String> modes) {
		this.modes = modes;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
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
