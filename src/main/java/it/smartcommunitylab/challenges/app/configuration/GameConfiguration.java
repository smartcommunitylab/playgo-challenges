package it.smartcommunitylab.challenges.app.configuration;

import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class GameConfiguration {
    private String gameId;
    private StandardSingleChallengeConfig standardSingleChallenges;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public StandardSingleChallengeConfig getStandardSingleChallenges() {
        return standardSingleChallenges;
    }

    public void setStandardSingleChallenges(
            StandardSingleChallengeConfig standardSingleChallenges) {
        this.standardSingleChallenges = standardSingleChallenges;
    }

    public Game toGame() {
        return new Game(gameId);
    }

    public StandardSingleChallenge toChallengeConfig() {
        return standardSingleChallenges.toChallengeConfig();
    }


}
