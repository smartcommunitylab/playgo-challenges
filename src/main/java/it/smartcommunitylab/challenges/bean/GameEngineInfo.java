package it.smartcommunitylab.challenges.bean;

public class GameEngineInfo {
    private final String url;
    private final String username;
    private final String password;
    private final String assign;
    private final String api_user;
    private final String api_pass;

    public GameEngineInfo(String url, String username, String password, String assign, String api_user, String api_pass) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.assign = assign;
        this.api_user = api_user;
        this.api_pass = api_pass;
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

    public String getApiUser() {
        return api_user;
    }

    public String getApiPass() {
        return api_pass;
    }

}
