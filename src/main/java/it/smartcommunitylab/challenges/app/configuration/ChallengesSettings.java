package it.smartcommunitylab.challenges.app.configuration;

import java.util.List;

import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.SpecialSingleChallenge;
import it.smartcommunitylab.challenges.bean.StandardGroupChallenge;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class ChallengesSettings {
    private Game game;
    private StandardSingleChallenge standardSingleChallengeConfig;
    private StandardGroupChallenge standardGroupChallengeConfig;
    private List<SpecialSingleChallenge> specialSingleChallengeConfig;

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

    public StandardGroupChallenge getStandardGroupChallengeConfig() {
        return standardGroupChallengeConfig;
    }

    public void setStandardGroupChallengeConfig(
            StandardGroupChallenge standardGroupChallengeConfig) {
        this.standardGroupChallengeConfig = standardGroupChallengeConfig;
    }

    public List<SpecialSingleChallenge> getSpecialSingleChallengeConfig() {
        return specialSingleChallengeConfig;
    }

    public void setSpecialSingleChallengeConfig(
            List<SpecialSingleChallenge> specialSingleChallengeConfig) {
        this.specialSingleChallengeConfig = specialSingleChallengeConfig;
    }

}
