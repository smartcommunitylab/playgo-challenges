package it.smartcommunitylab.challenges.bean;

import java.util.List;
import java.util.Set;

public class StandardSingleChallenge {
	private List<LevelStrategy> levelStrategies;
	private List<WeekStrategy> weekStrategies;
	private Settings settings;
	private Set<String> playerSet;
	private Reward reward;

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public Set<String> getPlayerSet() {
		return playerSet;
	}

	public void setPlayerSet(Set<String> playerSet) {
		this.playerSet = playerSet;
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public List<LevelStrategy> getLevelStrategies() {
		return levelStrategies;
	}

	public void setLevelStrategies(List<LevelStrategy> levelStrategies) {
		this.levelStrategies = levelStrategies;
	}

	public List<WeekStrategy> getWeekStrategies() {
		return weekStrategies;
	}

	public void setWeekStrategies(List<WeekStrategy> weekStrategies) {
		this.weekStrategies = weekStrategies;
	}

 
}
