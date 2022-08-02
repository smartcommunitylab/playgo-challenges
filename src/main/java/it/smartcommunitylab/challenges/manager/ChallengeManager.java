package it.smartcommunitylab.challenges.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import it.smartcommunitylab.challenges.Challenges;
import it.smartcommunitylab.challenges.ExecDate;
import it.smartcommunitylab.challenges.Result;
import it.smartcommunitylab.challenges.app.Tasker;
import it.smartcommunitylab.challenges.app.configuration.ChallengesSettings;
import it.smartcommunitylab.challenges.bean.ConfigurationStub;
import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.SpecialSingleChallenge;
import it.smartcommunitylab.challenges.bean.StandardGroupChallenge;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

@Service
public class ChallengeManager {

	@Autowired
	private Environment env;

	private static final Logger logger = LogManager.getLogger(ChallengeManager.class);

	public void invokeGenerator(List<ConfigurationStub> gameConfigs, String execDate, String task, String assign) {
		final ExecDate executionDate = execDate != null ? new ExecDate(execDate) : new ExecDate();
		logger.info("running on gamification engine url {}, execDate: {}", env.getProperty("config.url"),
				executionDate.getInstantAsString());
		final Tasker tasker = new Tasker(!isEmptyString(task) ? task.split(",") : new String[0]);
		GameEngineInfo gameEngineConf = new GameEngineInfo(
				env.getProperty("config.url"),
				assign, 
				env.getProperty("config.api_user"), 
				env.getProperty("config.api_pass"),
				env.getProperty("config.postgresUrl")
				);
		Challenges challenges = new Challenges(gameEngineConf);
		List<ChallengesSettings> challengesSettings = parseConfiguration(gameConfigs);
		challengesSettings.forEach(settings -> {
			final Game game = settings.getGame();
			if (tasker.isAssignStandardSingle()) {
				logger.info("execute standardSingleChallenges assignment");
				final StandardSingleChallenge standardSingleChallenges = settings.getStandardSingleChallengeConfig();
				Result result = challenges.generate(game, standardSingleChallenges, executionDate);
				logger.info("Terminated task {}", result.getResult());
			}
			if (tasker.isAssignStandardGroup()) {
				logger.info("execute standardGroupChallenges assignment");
				final StandardGroupChallenge standardGroupChallenges = settings.getStandardGroupChallengeConfig();
				Result result = challenges.generate(game, standardGroupChallenges, executionDate);
				logger.info("Terminated task {}", result.getResult());
			}
			if (tasker.isAssignSpecialSingle()) {
				logger.info("execute specialSingleChallenges assignment");
				final List<SpecialSingleChallenge> specialSingleChallenges = settings.getSpecialSingleChallengeConfig();
				if (specialSingleChallenges != null && !specialSingleChallenges.isEmpty()) {
					specialSingleChallenges.forEach(special -> challenges.generate(game, special, executionDate));
				}
				logger.info("Terminated task {}", true);
			}
		});
	}

	private List<ChallengesSettings> parseConfiguration(List<ConfigurationStub> gamesConfiguration) {
		return gamesConfiguration.stream().map(gc -> {
			ChallengesSettings settings = new ChallengesSettings();
			settings.setGame(new Game(gc.getGameId()));
			if (gc.getStandardSingleChallenge() != null) {
				settings.setStandardSingleChallengeConfig(gc.getStandardSingleChallenge().toChallengeConfig());
			}
			if (gc.getStandardGroupChallenge() != null) {
				settings.setStandardGroupChallengeConfig(gc.getStandardGroupChallenge().toChallengeConfig());
			}
			if (gc.getSpecialSingleChallenge() != null) {
				settings.setSpecialSingleChallengeConfig(
                        gc.getSpecialSingleChallenge().stream()
                                .map(special -> special.toChallengeConfig())
                                .collect(Collectors.toList()));
			}
			return settings;
		}).collect(Collectors.toList());
	}

	boolean isEmptyString(String string) {
		return string == null || string.isEmpty();
	}

}
