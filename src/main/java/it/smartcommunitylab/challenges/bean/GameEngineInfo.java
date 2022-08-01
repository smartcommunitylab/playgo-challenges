package it.smartcommunitylab.challenges.bean;

public class GameEngineInfo {
	private final String url;
	private final String assign;
	private final String api_user;
	private final String api_pass;
	private final String postgresUrl;

	public GameEngineInfo(String url, String assign, String api_user, String api_pass,
			String postgresUrl) {
		this.url = url;
		this.assign = assign;
		this.api_user = api_user;
		this.api_pass = api_pass;
		this.postgresUrl = postgresUrl;
	}

	public String getUrl() {
		return url;
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

    public String getPostgresUrl() {
		return postgresUrl;
	}

}
