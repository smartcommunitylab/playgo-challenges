package it.smartcommunitylab.challenges.app.configuration;

import java.util.ArrayList;
import java.util.List;

import it.smartcommunitylab.challenges.bean.Challenge;
import it.smartcommunitylab.challenges.bean.WeekStrategy;

public class WeekStrategyConfig {
	private int index;
	private List<ChallengesConfig> challenges;

	public static class ChallengesConfig {
		private Challenge challenge;

		public Challenge getChallenge() {
			return challenge;
		}

		public void setChallenge(Challenge challenge) {
			this.challenge = challenge;
		}

	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<ChallengesConfig> getChallenges() {
		return challenges;
	}

	public void setChallenges(List<ChallengesConfig> challenges) {
		this.challenges = challenges;
	}

	public List<Challenge> toChallenges() {
		List<Challenge> temp = new ArrayList<>();
		for (ChallengesConfig chg: challenges) {
			temp.add(chg.getChallenge());
		}
		return temp;
	}

	public WeekStrategy toCreationRule() {
		return new WeekStrategy(index, toChallenges());
	}

}