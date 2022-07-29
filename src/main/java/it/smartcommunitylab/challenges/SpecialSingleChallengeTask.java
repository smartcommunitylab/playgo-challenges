package it.smartcommunitylab.challenges;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.fbk.das.api.RecommenderSystemAPI;
import eu.fbk.das.model.ChallengeExpandedDTO;
import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.SpecialSingleChallenge;

public class SpecialSingleChallengeTask implements Runnable {

	private static final Logger logger = LogManager.getLogger(SpecialSingleChallengeTask.class);

	private GameEngineInfo gameEngineConf;
	private RecommenderSystemAPI recommenderApi;
	private Game game;
	private SpecialSingleChallenge specialSingleChallenge;

	@Override
	public void run() {
		System.out.println(System.currentTimeMillis() + " Runnable Task (SpecialSingleChallengeTask)" + " on thread "
				+ Thread.currentThread().getName());
		this.generate(game, gameEngineConf, specialSingleChallenge);
	}

	public SpecialSingleChallengeTask(Game game, GameEngineInfo gameEngineConf,
			SpecialSingleChallenge specialSingleChallenge) {
		this(game, gameEngineConf, specialSingleChallenge, new eu.fbk.das.api.RecommenderSystemImpl());
	}

	SpecialSingleChallengeTask(Game game, GameEngineInfo gameEngineConf, SpecialSingleChallenge specialSingleChallenge,
			RecommenderSystemAPI recommerderApiImpl) {
		this.game = game;
		this.gameEngineConf = gameEngineConf;
		this.specialSingleChallenge = specialSingleChallenge;
		this.recommenderApi = recommerderApiImpl;
	}

	public Result generate(Game game, GameEngineInfo gameEngineConf, SpecialSingleChallenge specialSingleChallenges) {
		logger.info("Generate special single challenge for gameId " + game.getGameId());
		final NextExecution nextChallengeExecution = new NextExecution(specialSingleChallenges, new ExecDate());
		Map<String, String> gameEngineConfs = ConfigConverter.toGameEngineConfs(game, gameEngineConf);
		Map<String, Object> challengeValues = ConfigConverter.toSpecialChallengeValues(nextChallengeExecution,
				specialSingleChallenges);
		String playerSet = ConfigConverter.toPlayerSet(specialSingleChallenges.getPlayerSet());
		Map<String, String> rewards = ConfigConverter.toRewards(specialSingleChallenges.getReward());

		final String model = specialSingleChallenges.getSettings().getModel();
		List<ChallengeExpandedDTO> challenges = recommenderApi.createSpecialSingleChallenges(gameEngineConfs, model,
				challengeValues, playerSet, rewards);
		if (logger.isDebugEnabled()) {
			challenges.stream().map(c -> new Challenge(c)).forEach(c -> {
				logger.debug("playerId:{}, instanceName:{}, model:{}, s:{}, e:{}, f:{}", c.playerId(), c.instanceName(),
						c.modelName(), c.start(), c.end(), c.data());
			});
		}
		logger.info("Created {} challenges of model {} for game {}", challenges.size(), model, game.getGameId());
		if (this.gameEngineConf.getAssign()) {
			challenges.forEach(challenge -> {
				recommenderApi.assignSingleChallenge(gameEngineConfs, challenge);
			});
			logger.info("Assigned {} challenges of model {} for game {}", challenges.size(), model, game.getGameId());
		}
		return new ValidResult(true);
	}

}