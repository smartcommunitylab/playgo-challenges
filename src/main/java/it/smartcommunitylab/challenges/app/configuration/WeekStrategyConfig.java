package it.smartcommunitylab.challenges.app.configuration;

import it.smartcommunitylab.challenges.bean.WeekStrategy;

public class WeekStrategyConfig {
	private int index;
	private String strategy;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public WeekStrategy toCreationRule() {
		return new WeekStrategy(index, strategy);
	}
}
