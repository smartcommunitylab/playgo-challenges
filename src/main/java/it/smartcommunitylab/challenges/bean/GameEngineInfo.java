package it.smartcommunitylab.challenges.bean;

public class GameEngineInfo {
    private final String url;
    private final String username;
    private final String password;
    private final String assign;

    public GameEngineInfo(String url, String username, String password, String assign) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.assign = assign;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getAssign() {
        return assign != null && assign.equals("true");
    }

}
