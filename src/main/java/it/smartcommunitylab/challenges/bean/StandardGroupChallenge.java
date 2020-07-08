package it.smartcommunitylab.challenges.bean;

import java.util.Set;

public class StandardGroupChallenge {
    private GroupSettings settings;
    private Set<String> playerSet;
    private Reward reward;

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

    public GroupSettings getSettings() {
        return settings;
    }

    public void setSettings(GroupSettings settings) {
        this.settings = settings;
    }
}
