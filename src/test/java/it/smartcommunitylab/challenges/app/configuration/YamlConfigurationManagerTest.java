package it.smartcommunitylab.challenges.app.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.Period;
import java.util.List;

import org.junit.Test;

import it.smartcommunitylab.challenges.bean.Settings;

public class YamlConfigurationManagerTest {

    @Test
    public void parse_standard_single_challenge_configuration() throws ParseException {
        ConfigurationManager confManager = new YamlConfigurationManager();
        List<ChallengesSettings> settings =
                confManager.parseConfiguration(
                ClassLoader.getSystemResourceAsStream("config.yml"));
        assertThat(settings).hasSize(1);
        ChallengesSettings conf = settings.get(0);
        assertThat(conf.getGame().getGameId()).isEqualTo("5d9353a3f0856342b2dded7f");
        assertThat(conf.getStandardSingleChallengeConfig().getLevelStrategies()).hasSize(4);
        Settings config = conf.getStandardSingleChallengeConfig().getSettings();
        assertThat(config.getStart())
                .isEqualTo("2019-10-26 00:00:00");
        assertThat(config.getDuration()).isEqualTo(Period.ofDays(7));
        assertThat(conf.getStandardSingleChallengeConfig().getPlayerSet()).isEmpty();
    }
}
