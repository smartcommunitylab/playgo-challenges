package it.smartcommunitylab.challenges.app.configuration;

import java.util.List;
import java.util.stream.Collectors;

import it.smartcommunitylab.challenges.bean.HighSchoolChallenge;

public class HighSchoolChallengeConfig {
	private HighSchoolChallengeSettings settings;
	private List<WeekStrategyConfig> weekStrategies;

	public void setSettings(HighSchoolChallengeSettings settings) {
		this.settings = settings;
	}

	public HighSchoolChallengeSettings getSettings() {
		return settings;
	}

	public List<WeekStrategyConfig> getWeekStrategies() {
		return weekStrategies;
	}

	public void setWeekStrategies(List<WeekStrategyConfig> weekStrategies) {
		this.weekStrategies = weekStrategies;
	}

	public HighSchoolChallenge toChallengeConfig() {
		HighSchoolChallenge hscConfig = new HighSchoolChallenge();
		hscConfig.setSettings(settings.toConfig());
		hscConfig
				.setWeekStrategies(weekStrategies.stream().map(cr -> cr.toCreationRule()).collect(Collectors.toList()));
		return hscConfig;
	}

}
