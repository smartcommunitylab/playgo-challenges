package it.smartcommunitylab.challenges.bean;

import java.util.List;

public class HighSchoolChallenge {
	private List<WeekStrategy> weekStrategies;
	private Settings settings;

	public List<WeekStrategy> getWeekStrategies() {
		return weekStrategies;
	}

	public void setWeekStrategies(List<WeekStrategy> weekStrategies) {
		this.weekStrategies = weekStrategies;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

}
