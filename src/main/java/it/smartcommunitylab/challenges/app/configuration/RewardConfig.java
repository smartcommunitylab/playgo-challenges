package it.smartcommunitylab.challenges.app.configuration;

import it.smartcommunitylab.challenges.bean.Reward;
import it.smartcommunitylab.challenges.bean.Reward.RewardType;

public class RewardConfig {
    private String scoreName;
    private String type;
    private double value;
    private double maxValue;

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public Reward toReward() {
        try {
            // type is null for standardGroupChallenges
            final RewardType rewardType =
                    type != null ? RewardType.valueOf(type.toUpperCase()) : null;
            Reward reward = new Reward(scoreName, rewardType, value);
            if (maxValue > 0) {
                reward.setMaxReward(maxValue);
            }
            return reward;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(
                    "valid reward type values are: fixed, bonus, booster, %s not found",
                    type));
        }
    }



}
