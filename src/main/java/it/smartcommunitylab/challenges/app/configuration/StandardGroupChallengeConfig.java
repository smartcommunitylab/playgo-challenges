package it.smartcommunitylab.challenges.app.configuration;

import java.util.HashSet;
import java.util.List;

import it.smartcommunitylab.challenges.bean.StandardGroupChallenge;

public class StandardGroupChallengeConfig {
    private String model;

    private List<String> playerSet;
    private RewardConfig reward;
    private StandardGroupSettings settings;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

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

    public StandardGroupSettings getSettings() {
        return settings;
    }

    public void setSettings(StandardGroupSettings settings) {
        this.settings = settings;
    }

    public StandardGroupChallenge toChallengeConfig() {
        StandardGroupChallenge config = new StandardGroupChallenge();
        config.setPlayerSet(new HashSet<>(playerSet));
        config.setReward(reward.toReward());
        config.setSettings(settings.toConfig());
        return config;
    }


}
