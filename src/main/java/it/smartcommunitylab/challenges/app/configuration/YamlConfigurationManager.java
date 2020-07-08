package it.smartcommunitylab.challenges.app.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import it.smartcommunitylab.challenges.bean.Game;

public class YamlConfigurationManager implements ConfigurationManager {
    private static final Logger logger = LogManager.getLogger(YamlConfigurationManager.class);
    private ObjectMapper mapper;

    public YamlConfigurationManager() {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        mapper.configOverride(List.class)
                .setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Period.class, new PeriodDeserializer());
        mapper.registerModule(module);
    }


    @Override
    public List<ChallengesSettings> parseConfiguration(InputStream source) {
        try {
            GamesConfiguration gamesConfiguration =
                    mapper.readValue(source, GamesConfiguration.class);
            return gamesConfiguration.getGames().stream().map(gc -> {
                ChallengesSettings settings = new ChallengesSettings();
                settings.setGame(new Game(gc.getGameId()));
                if (gc.getStandardSingleChallenges() != null) {
                settings.setStandardSingleChallengeConfig(
                        gc.getStandardSingleChallenges().toChallengeConfig());
                }
                if (gc.getStandardGroupChallenges() != null) {
                settings.setStandardGroupChallengeConfig(
                        gc.getStandardGroupChallenges().toChallengeConfig());
                }
                return settings;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e);
            return Collections.emptyList();
        }

    }
}
