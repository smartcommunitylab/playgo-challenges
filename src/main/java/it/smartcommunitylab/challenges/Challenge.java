package it.smartcommunitylab.challenges;

import java.util.Date;
import java.util.Map;

import eu.fbk.das.model.ChallengeExpandedDTO;

class Challenge {
	private ChallengeExpandedDTO expandedChallenge;
	
	private static final String PLAYER_ID_KEY = "pId";
	
	public Challenge(ChallengeExpandedDTO expandedChallenge) {
		this.expandedChallenge = expandedChallenge;
	}
	
	public String modelName() {
		return expandedChallenge.getModelName();
	}
	
	public Date start() {
		return expandedChallenge.getStart();
	}
	
	public Date end() {
		return expandedChallenge.getEnd();
	}
	
	public Map<String,Object> data() {
		return expandedChallenge.getData();
	}
	
	public String instanceName() {
		return expandedChallenge.getInstanceName();
	}
	
	public String playerId() {
		return (String) expandedChallenge.getInfo(PLAYER_ID_KEY);
	}
}
