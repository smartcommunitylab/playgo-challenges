package it.smartcommunitylab.challenges.app.configuration;

import it.smartcommunitylab.challenges.bean.LevelStrategy;
import it.smartcommunitylab.challenges.bean.Level;

public class LevelStrategyConfig {
    private LevelConfig level;
    private String strategy;

    public static class LevelConfig {
        private String type;
        private int index;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Level toLevel() {
            return new Level(type, index);
        }

    }

    public LevelConfig getLevel() {
        return level;
    }

    public void setLevel(LevelConfig level) {
        this.level = level;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public LevelStrategy toCreationRule() {
        return new LevelStrategy(level.toLevel(), strategy);
    }
}
