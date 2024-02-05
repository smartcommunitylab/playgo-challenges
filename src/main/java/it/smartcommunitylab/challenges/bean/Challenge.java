package it.smartcommunitylab.challenges.bean;

import java.util.Set;

import it.smartcommunitylab.challenges.app.configuration.RewardConfig;

public class Challenge {
	private Set<String> playerSet;
	private String challengeTyp;
	private String strategy;
	private Set<String> pointConcepts;
	private RewardConfig reward;

	public Set<String> getPlayerSet() {
		return playerSet;
	}

	public void setPlayerSet(Set<String> playerSet) {
		this.playerSet = playerSet;
	}

	public String getChallengeTyp() {
		return challengeTyp;
	}

	public void setChallengeTyp(String challengeTyp) {
		this.challengeTyp = challengeTyp;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public Set<String> getPointConcepts() {
		return pointConcepts;
	}

	public void setPointConcepts(Set<String> pointConcepts) {
		this.pointConcepts = pointConcepts;
	}

	public RewardConfig getReward() {
		return reward;
	}

	public void setReward(RewardConfig reward) {
		this.reward = reward;
	}

}
