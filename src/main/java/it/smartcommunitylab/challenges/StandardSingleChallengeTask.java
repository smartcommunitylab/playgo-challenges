package it.smartcommunitylab.challenges;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.fbk.das.api.RecommenderSystemAPI;
import eu.fbk.das.model.ChallengeExpandedDTO;
import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class StandardSingleChallengeTask implements Runnable {

	private static final Logger logger = LogManager.getLogger(StandardSingleChallengeTask.class);

	private GameEngineInfo gameEngineConf;
	private RecommenderSystemAPI recommenderApi;
	private Game game;
	private StandardSingleChallenge standardSingleChallenge;

	@Override
	public void run() {
		System.out.println(System.currentTimeMillis() + " Runnable Task (StandardSingleChallengeTask)" + " on thread "
				+ Thread.currentThread().getName());
		this.generate(game, gameEngineConf, standardSingleChallenge);
	}

	public StandardSingleChallengeTask(Game game, GameEngineInfo gameEngineConf,
			StandardSingleChallenge standardSingleChallenges) {
		this(game, gameEngineConf, standardSingleChallenges, new eu.fbk.das.api.RecommenderSystemImpl());
	}

	StandardSingleChallengeTask(Game game, GameEngineInfo gameEngineConf,
			StandardSingleChallenge standardSingleChallenge, RecommenderSystemAPI recommerderApiImpl) {
		this.game = game;
		this.gameEngineConf = gameEngineConf;
		this.standardSingleChallenge = standardSingleChallenge;
		this.recommenderApi = recommerderApiImpl;
	}

	public Result generate(Game game, GameEngineInfo gameEngineConf, StandardSingleChallenge standardSingleChallenges) {
		logger.info("Generate standard single challenge for gameId " + game.getGameId());
		final NextExecution nextChallengeExecution = new NextExecution(standardSingleChallenges, new ExecDate());
		if (nextChallengeExecution.isSuspended()) {
			logger.info("challenge will start in a suspension range, suspend assignment");
			return new ValidResult(true);
		} else {
			Map<String, String> gameEngineConfs = ConfigConverter.toGameEngineConfs(game, gameEngineConf);
			Map<String, String> creationRules = ConfigConverter.toCreationRules(standardSingleChallenges);
			Map<String, Object> challengeValues = ConfigConverter.toChallengeValues(nextChallengeExecution);
			String playerSet = ConfigConverter.toPlayerSet(standardSingleChallenges.getPlayerSet());
			Map<String, String> rewards = ConfigConverter.toRewards(standardSingleChallenges.getReward());
			Set<String> modes = standardSingleChallenges.getSettings().getModes();

			List<ChallengeExpandedDTO> challenges = recommenderApi.createStandardSingleChallenges(gameEngineConfs,
					modes, creationRules, challengeValues, playerSet, rewards);
			if (logger.isDebugEnabled()) {
				challenges.stream().map(c -> new it.smartcommunitylab.challenges.Challenge(c)).forEach(c -> {
					logger.debug("playerId:{}, instanceName:{}, model:{}, s:{}, e:{}, f:{}", c.playerId(),
							c.instanceName(), c.modelName(), c.start(), c.end(), c.data());
				});
			}
			logger.info("Created {} challenges for game {}", challenges.size(), game.getGameId());
			if (this.gameEngineConf.getAssign()) {
				challenges.forEach(challenge -> {
					recommenderApi.assignSingleChallenge(gameEngineConfs, challenge);
				});
				logger.info("Assigned {} challenges for game {}", challenges.size(), game.getGameId());
			}
			return new ValidResult(true);
		}
	}

}
