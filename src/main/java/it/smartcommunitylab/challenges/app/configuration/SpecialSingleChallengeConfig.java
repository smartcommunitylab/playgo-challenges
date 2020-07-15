package it.smartcommunitylab.challenges.app.configuration;

import java.util.HashSet;
import java.util.List;

import it.smartcommunitylab.challenges.bean.SpecialSingleChallenge;

public class SpecialSingleChallengeConfig {
    private SpecialSingleSettings settings;
    private List<String> playerSet;
    private RewardConfig reward;


    public List<String> getPlayerSet() {
        return playerSet;
    }

    public void setPlayerSet(List<String> playerSet) {
        this.playerSet = playerSet;
    }

    public RewardConfig getReward() {
        return reward;
    }

    public void setReward(RewardConfig reward) {
        this.reward = reward;
    }

    public SpecialSingleChallenge toChallengeConfig() {
        SpecialSingleChallenge special = new SpecialSingleChallenge();
        special.setSettings(settings.toConfig());
        special.setPlayerSet(new HashSet<>(playerSet));
        special.setReward(reward.toReward());
        return special;

    }

    public SpecialSingleSettings getSettings() {
        return settings;
    }

    public void setSettings(SpecialSingleSettings settings) {
        this.settings = settings;
    }
}
