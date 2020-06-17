package it.smartcommunitylab.challenges.app.configuration;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class StandardSingleChallengeConfig {
    private List<LevelStrategyConfig> levelStrategies;
    private StandardSingleSettings settings;
    private List<String> playerSet;
    private RewardConfig reward;


    public StandardSingleSettings getSettings() {
        return settings;
    }

    public void setSettings(StandardSingleSettings settings) {
        this.settings = settings;
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

    public List<LevelStrategyConfig> getLevelStrategies() {
        return levelStrategies;
    }

    public void setLevelStrategies(List<LevelStrategyConfig> levelStrategies) {
        this.levelStrategies = levelStrategies;
    }

    public StandardSingleChallenge toChallengeConfig() {
        StandardSingleChallenge standardConfig = new StandardSingleChallenge();
        standardConfig.setPlayerSet(new HashSet<>(playerSet));
        standardConfig.setSettings(settings.toConfig());
        standardConfig.setLevelStrategies(
                levelStrategies.stream().map(cr -> cr.toCreationRule()).collect(Collectors.toList()));
        standardConfig.setReward(reward.toReward());
        return standardConfig;
    }


}
