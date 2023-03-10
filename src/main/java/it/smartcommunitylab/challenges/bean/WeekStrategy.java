package it.smartcommunitylab.challenges.bean;

public class WeekStrategy {
	private int index;
	private String strategy;

	public WeekStrategy(int index, String strategy) {
		this.index = index;
		this.strategy = strategy;
	}

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

}
