package it.smartcommunitylab.challenges;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Period;
import java.util.Map;

import it.smartcommunitylab.challenges.app.CliOptions;
import org.junit.Test;

import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.Settings;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class ConfigConverterTest {

    @Test
    public void check_game_engine_config() {
        GameEngineInfo engineConfig = new GameEngineInfo("url", "true", "username", "pass", "postgresUrl");
        Game game = new Game("gameId");
        Map<String, String> result = ConfigConverter.toGameEngineConfs(game, engineConfig);
        assertThat(result).containsKeys("HOST", "USER", "PASS", "GAMEID");
    }

    @Test
    public void check_challenge_config() {
        StandardSingleChallenge standardSingleChallenges =
                new StandardSingleChallenge();
        standardSingleChallenges
                .setSettings(new Settings(DateHelper.dateFromIso("2020-06-20"), Period.ofDays(7)));
        Map<String, Object> result =
                ConfigConverter.toChallengeValues(
                        new NextExecution(standardSingleChallenges, new ExecDate("2020-06-11")));
        assertThat(result).containsKeys("start", "duration", "challengeWeek", "exec", "hide");
    }
}
