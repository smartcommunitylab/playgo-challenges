package it.smartcommunitylab.challenges.app.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.smartcommunitylab.challenges.bean.GroupSettings;
import it.smartcommunitylab.challenges.bean.Settings;
import it.smartcommunitylab.challenges.bean.SpecialSettings;
import it.smartcommunitylab.challenges.bean.SpecialSingleChallenge;

public class YamlConfigurationManagerTest {

    @Test
    public void parse_standard_single_challenge_configuration() throws ParseException {
        ConfigurationManager confManager = new YamlConfigurationManager();
        List<ChallengesSettings> settings =
                confManager.parseConfiguration(
                        ClassLoader.getSystemResourceAsStream("config-standard-single.yml"));
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

    @Test
    public void parse_standard_single_challenge_with_playerSet() throws ParseException {
        ConfigurationManager confManager = new YamlConfigurationManager();
        List<ChallengesSettings> settings =
                confManager.parseConfiguration(
                        ClassLoader.getSystemResourceAsStream(
                                "config-standard-single-with-playerSet.yml"));
        assertThat(settings).hasSize(1);
        ChallengesSettings conf = settings.get(0);
        assertThat(conf.getGame().getGameId()).isEqualTo("5d9353a3f0856342b2dded7f");
        assertThat(conf.getStandardSingleChallengeConfig().getLevelStrategies()).hasSize(4);
        Settings config = conf.getStandardSingleChallengeConfig().getSettings();
        assertThat(config.getStart()).isEqualTo("2019-10-26 00:00:00");
        assertThat(config.getDuration()).isEqualTo(Period.ofDays(7));
        assertThat(conf.getStandardSingleChallengeConfig().getPlayerSet()).hasSize(2);
    }


    @Test
    public void parse_standard_group_challenge_configuration() throws ParseException {
        ConfigurationManager confManager = new YamlConfigurationManager();
        List<ChallengesSettings> settings =
                confManager.parseConfiguration(
                        ClassLoader.getSystemResourceAsStream("config-standard-group.yml"));
        assertThat(settings).hasSize(1);
        ChallengesSettings conf = settings.get(0);
        assertThat(conf.getGame().getGameId()).isEqualTo("5d9353a3f0856342b2dded7f");
        assertThat(conf.getStandardSingleChallengeConfig()).isNull();

        GroupSettings config = conf.getStandardGroupChallengeConfig().getSettings();
        assertThat(config.getStart()).isEqualTo("2020-11-06 00:00:00");
        assertThat(config.getDuration()).isEqualTo(Period.ofDays(2));
        assertThat(conf.getStandardGroupChallengeConfig().getPlayerSet()).isEmpty();
        assertThat(conf.getStandardGroupChallengeConfig().getSettings().getModel())
                .isEqualTo("groupCompetitiveTime");
    }

    @Test
    public void parse_special_single_challenge_configuration() throws ParseException {
        ConfigurationManager confManager = new YamlConfigurationManager();
        List<ChallengesSettings> settings = confManager.parseConfiguration(
                ClassLoader.getSystemResourceAsStream("config-special-single.yml"));
        assertThat(settings).hasSize(1);
        ChallengesSettings conf = settings.get(0);
        assertThat(conf.getGame().getGameId()).isEqualTo("5d9353a3f0856342b2dded7f");
        assertThat(conf.getStandardSingleChallengeConfig()).isNull();
        assertThat(conf.getStandardGroupChallengeConfig()).isNull();

        final SpecialSingleChallenge specialSingleChallenge =
                conf.getSpecialSingleChallengeConfig().get(0);
        SpecialSettings config = specialSingleChallenge.getSettings();
        assertThat(config.getStart()).isEqualTo("2020-05-01 00:00:00");
        assertThat(config.getDuration()).isEqualTo(Period.ofDays(1));
        Map<String, Object> fields = new HashMap<>();
        fields.put("surveyType", "finalCountdown");
        fields.put("bonusPointType", "green leaves");
        fields.put("link", "");
        assertThat(config.getFields()).containsAllEntriesOf(fields);
        assertThat(specialSingleChallenge.getPlayerSet()).isEmpty();
    }
}
