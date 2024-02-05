package it.smartcommunitylab.challenges.bean;

import java.util.List;

public class WeekStrategy {
	private int index;
	private List<Challenge> challenge;

	public WeekStrategy(int index, List<Challenge> challenge) {
		super();
		this.index = index;
		this.challenge = challenge;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<Challenge> getChallenge() {
		return challenge;
	}

	public void setChallenge(List<Challenge> challenge) {
		this.challenge = challenge;
	}

}