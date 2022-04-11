package it.smartcommunitylab.challenges.bean;

import java.util.List;

import it.smartcommunitylab.challenges.app.configuration.SpecialSingleChallengeConfig;
import it.smartcommunitylab.challenges.app.configuration.StandardGroupChallengeConfig;
import it.smartcommunitylab.challenges.app.configuration.StandardSingleChallengeConfig;

public class ConfigurationStub {
	private String gameId;
	private StandardSingleChallengeConfig standardSingleChallenge;
	private StandardGroupChallengeConfig standardGroupChallenge;
	private List<SpecialSingleChallengeConfig> specialSingleChallenge;

	public ConfigurationStub(String gameId, StandardSingleChallengeConfig standardSingleChallenge,
			StandardGroupChallengeConfig standardGroupChallenge,
			List<SpecialSingleChallengeConfig> specialSingleChallenge) {
		super();
		this.gameId = gameId;
		this.standardSingleChallenge = standardSingleChallenge;
		this.standardGroupChallenge = standardGroupChallenge;
		this.specialSingleChallenge = specialSingleChallenge;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public StandardSingleChallengeConfig getStandardSingleChallenge() {
		return standardSingleChallenge;
	}

	public void setStandardSingleChallenge(StandardSingleChallengeConfig standardSingleChallenge) {
		this.standardSingleChallenge = standardSingleChallenge;
	}

	public StandardGroupChallengeConfig getStandardGroupChallenge() {
		return standardGroupChallenge;
	}

	public void setStandardGroupChallenge(StandardGroupChallengeConfig standardGroupChallenge) {
		this.standardGroupChallenge = standardGroupChallenge;
	}

	public List<SpecialSingleChallengeConfig> getSpecialSingleChallenge() {
		return specialSingleChallenge;
	}

	public void setSpecialSingleChallenge(List<SpecialSingleChallengeConfig> specialSingleChallenge) {
		this.specialSingleChallenge = specialSingleChallenge;
	}

}
