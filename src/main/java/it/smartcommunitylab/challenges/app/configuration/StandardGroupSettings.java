package it.smartcommunitylab.challenges.app.configuration;

import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.smartcommunitylab.challenges.bean.GroupSettings;

public class StandardGroupSettings {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Rome")
	private Date start;
	private Period duration;
	private String model;
	private List<String> modeConcepts;
	private int minLevel;
	private Map<String, Double> modeMax;
	private Map<String, Double> modeMin;

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

	public GroupSettings toConfig() {
		GroupSettings settings = new GroupSettings(start, duration, modeMax, modeMin);
		settings.setModel(model);
		settings.setModes(new HashSet<>(modeConcepts));
		settings.setMinLevel(minLevel);
		return settings;
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
