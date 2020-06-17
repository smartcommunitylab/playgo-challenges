package it.smartcommunitylab.challenges.bean;

public class Level {
    private String type;
    private int index;

    public Level(String type, int index) {
        this.type = type;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }


}
