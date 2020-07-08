package it.smartcommunitylab.challenges.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.smartcommunitylab.challenges.Challenges;
import it.smartcommunitylab.challenges.Result;
import it.smartcommunitylab.challenges.app.CliOptions.Options;
import it.smartcommunitylab.challenges.app.configuration.ChallengesSettings;
import it.smartcommunitylab.challenges.app.configuration.ConfigurationManager;
import it.smartcommunitylab.challenges.app.configuration.YamlConfigurationManager;
import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        CliOptions options = new CliOptions(args);
        logger.info("running on configuration {} and gamification engine url {}",
                options.get(Options.CONFIG), options.get(Options.URL));

        GameEngineInfo gameEngineConf =
                new GameEngineInfo(
                        options.get(Options.URL),
                options.get(Options.USERNAME), options.get(Options.PASSWORD));
        Challenges challenges = new Challenges(gameEngineConf);
        final String configPath = options.get(Options.CONFIG);
        ConfigurationManager configurationManager = new YamlConfigurationManager();
        List<ChallengesSettings> challengesSettings;
        try {
            challengesSettings =
                    configurationManager.parseConfiguration(new FileInputStream(configPath));
            challengesSettings.forEach(settings -> {
                final Game game = settings.getGame();
                final StandardSingleChallenge standardSingleChallenges =
                        settings.getStandardSingleChallengeConfig();
                Result result = challenges.assign(game, standardSingleChallenges);
                logger.info("Terminated assignment {}", result.getResult());
            });
        } catch (FileNotFoundException e) {
            logger.error("configuration file doesn't exist", e);
        }
    }
}
