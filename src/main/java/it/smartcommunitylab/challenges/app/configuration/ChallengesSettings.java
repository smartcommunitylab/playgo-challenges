package it.smartcommunitylab.challenges.app.configuration;

import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class ChallengesSettings {
    private Game game;
    private StandardSingleChallenge standardSingleChallengeConfig;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public StandardSingleChallenge getStandardSingleChallengeConfig() {
        return standardSingleChallengeConfig;
    }

    public void setStandardSingleChallengeConfig(
            StandardSingleChallenge standardSingleChallengeConfig) {
        this.standardSingleChallengeConfig = standardSingleChallengeConfig;
    }

}
