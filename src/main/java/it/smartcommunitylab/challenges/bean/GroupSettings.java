package it.smartcommunitylab.challenges.bean;

import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class GroupSettings {
	private Date start;
	private Period duration;
	private String model;
	private Set<String> modes;
	private int minLevel;

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

}
