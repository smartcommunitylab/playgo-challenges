package it.smartcommunitylab.challenges.bean;

import java.util.Set;

public class SpecialSingleChallenge {
    private SpecialSettings settings;
    private Set<String> playerSet;
    private Reward reward;

    public SpecialSettings getSettings() {
        return settings;
    }

    public void setSettings(SpecialSettings settings) {
        this.settings = settings;
    }

    public Set<String> getPlayerSet() {
        return playerSet;
    }

    public void setPlayerSet(Set<String> playerSet) {
        this.playerSet = playerSet;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }


}
