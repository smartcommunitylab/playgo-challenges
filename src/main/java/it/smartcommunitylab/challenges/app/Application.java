package it.smartcommunitylab.challenges.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.smartcommunitylab.challenges.Challenges;
import it.smartcommunitylab.challenges.Result;
import it.smartcommunitylab.challenges.app.configuration.ChallengesSettings;
import it.smartcommunitylab.challenges.app.configuration.ConfigurationManager;
import it.smartcommunitylab.challenges.app.configuration.YamlConfigurationManager;
import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class Application {

    public static void main(String[] args) {
        Map<String,String> options = getOptions(args);
        System.out.println(getOptions(args));
        
        GameEngineInfo gameEngineConf = new GameEngineInfo(options.get("url"), options.get("username"), options.get("password"));
        Challenges challenges = new Challenges(gameEngineConf);
        final String configPath = options.get("config");
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
                System.out.println(String.format("Terminated assignment %s", result.getResult()));
            });
        } catch (FileNotFoundException e) {
            System.out.println("configuration file doesn't exist");
            e.printStackTrace();
        }
    }

    private static final Map<String, String> getOptions(String[] args) {
        Map<String, String> options = new HashMap<>();
        for(int i = 0; i < args.length; i++) {
            String option = args[i];
            if(option.startsWith("--")) {
                options.put(option.substring(2), args[i + 1]);
            }
        }
        return options;
    }

}
