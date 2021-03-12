package it.smartcommunitylab.challenges;

import static org.assertj.core.api.Assertions.assertThat;

import it.smartcommunitylab.challenges.app.CliOptions;
import org.junit.Test;

import it.smartcommunitylab.challenges.bean.GameEngineInfo;

public class MainTest {

    @Test
    public void instantiate_library() {
        GameEngineInfo gameEngineConf = new GameEngineInfo("engineUrl", "my-user", "p@ssW0rd", "", "", "");
        Challenges challenges = new Challenges(gameEngineConf);
        assertThat(challenges).isNotNull();
    }

}
