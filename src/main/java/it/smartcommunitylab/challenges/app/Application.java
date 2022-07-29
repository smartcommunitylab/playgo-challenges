//package it.smartcommunitylab.challenges.app;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.util.List;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import it.smartcommunitylab.challenges.Challenges;
//import it.smartcommunitylab.challenges.ExecDate;
//import it.smartcommunitylab.challenges.Result;
//import it.smartcommunitylab.challenges.app.CliOptions.Options;
//import it.smartcommunitylab.challenges.app.configuration.ChallengesSettings;
//import it.smartcommunitylab.challenges.app.configuration.ConfigurationManager;
//import it.smartcommunitylab.challenges.app.configuration.YamlConfigurationManager;
//import it.smartcommunitylab.challenges.bean.Game;
//import it.smartcommunitylab.challenges.bean.GameEngineInfo;
//import it.smartcommunitylab.challenges.bean.SpecialSingleChallenge;
//import it.smartcommunitylab.challenges.bean.StandardGroupChallenge;
//import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;
//
//import static eu.fbk.das.utils.Utils.p;
//
//public class Application {
//
//    private static final Logger logger = LogManager.getLogger(Application.class);
//
//    public static void main(String[] args) {
//        CliOptions options = new CliOptions(args);
//        final ExecDate executionDate = options.get(Options.EXEC_DATE) != null
//                ? new ExecDate(options.get(Options.EXEC_DATE)) : new ExecDate();
//        logger.info("running on configuration {} and gamification engine url {}, execDate: {}",
//                options.get(Options.CONFIG), options.get(Options.URL),
//                executionDate.getInstantAsString());
//        final Tasker tasker = new Tasker(options.getAsArray(Options.TASK));
//        GameEngineInfo gameEngineConf = new GameEngineInfo(
//        		options.get(Options.URL),
//        		options.get(Options.ASSIGN),
//        		options.get(Options.API_USER),
//        		options.get(Options.API_PASS),
//        		options.get(Options.POSTGRESURL)
//        		);
//        Challenges challenges = new Challenges(gameEngineConf);
//        final String configPath = options.get(Options.CONFIG);
//        ConfigurationManager configurationManager = new YamlConfigurationManager();
//        List<ChallengesSettings> challengesSettings;
//        try {
//            challengesSettings =
//                    configurationManager.parseConfiguration(new FileInputStream(configPath));
//            challengesSettings.forEach(settings -> {
//                final Game game = settings.getGame();
//                if (tasker.isAssignStandardSingle()) {
//                    logger.info("execute standardSingleChallenges assignment");
//                    final StandardSingleChallenge standardSingleChallenges =
//                            settings.getStandardSingleChallengeConfig();
//                    Result result =
//                            challenges.generate(game, standardSingleChallenges, executionDate);
//                    logger.info("Terminated task {}", result.getResult());
//                }
//                if (tasker.isAssignStandardGroup()) {
//                    logger.info("execute standardGroupChallenges assignment");
//                    final StandardGroupChallenge standardGroupChallenges =
//                            settings.getStandardGroupChallengeConfig();
//                    Result result = challenges.generate(game, standardGroupChallenges, executionDate);
//                    logger.info("Terminated task {}", result.getResult());
//                }
//                if (tasker.isAssignSpecialSingle()) {
//                    logger.info("execute specialSingleChallenges assignment");
//                    final List<SpecialSingleChallenge> specialSingleChallenges =
//                            settings.getSpecialSingleChallengeConfig();
//                    specialSingleChallenges
//                            .forEach(special -> challenges.generate(game, special, executionDate));
//                    logger.info("Terminated task {}", true);
//                }
//            });
//        } catch (FileNotFoundException e) {
//            logger.error("configuration file doesn't exist", e);
//        }
//    }
//}
