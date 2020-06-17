package it.smartcommunitylab.challenges.bean;


public class LevelStrategy {
    private Level level;
    private String strategy;

    public LevelStrategy(Level level, String strategy) {
        this.level = level;
        this.strategy = strategy;
    }

    public Level getLevel() {
        return level;
    }

    public String getStrategy() {
        return strategy;
    }



}
