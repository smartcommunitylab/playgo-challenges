package it.smartcommunitylab.challenges.bean;

import java.util.Optional;

public class Reward {
    private String scoreName;
    private RewardType type;
    private double value;
    private Double maxValue;

    public Reward(String scoreName, RewardType type, double value) {
        if (scoreName == null || scoreName.isEmpty()) {
            throw new IllegalArgumentException("scoreName is required");
        }
        if (value < 0) {
            throw new IllegalArgumentException("value cannot be less than 0");
        }
        this.scoreName = scoreName;
        this.type = type;
        this.value = value;
    }

    public enum RewardType {
        FIXED, BONUS, BOOSTER
    }

    public String getScoreName() {
        return scoreName;
    }

    public RewardType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public Optional<Double> getMaxValue() {
        return Optional.ofNullable(maxValue);
    }

    public void setMaxReward(double maxValue) {
        this.maxValue = maxValue;
    }
}
