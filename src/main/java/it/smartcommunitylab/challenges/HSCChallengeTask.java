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
import it.smartcommunitylab.challenges.bean.HighSchoolChallenge;

public class HSCChallengeTask implements Runnable {

	private static final Logger logger = LogManager.getLogger(HSCChallengeTask.class);

	private GameEngineInfo gameEngineConf;
	private RecommenderSystemAPI recommenderApi;
	private Game game;
	private HighSchoolChallenge highSchoolChallenge;

	@Override
	public void run() {
		System.out.println(System.currentTimeMillis() + " Runnable Task (HSCChallengeTask)" + " on thread "
				+ Thread.currentThread().getName());
		this.generate(game, gameEngineConf, highSchoolChallenge);
	}

	public HSCChallengeTask(Game game, GameEngineInfo gameEngineConf, HighSchoolChallenge highSchoolChallenge) {
		this(game, gameEngineConf, highSchoolChallenge, new eu.fbk.das.api.RecommenderSystemImpl());
	}

	HSCChallengeTask(Game game, GameEngineInfo gameEngineConf, HighSchoolChallenge hscChallenge,
			RecommenderSystemAPI recommerderApiImpl) {
		this.game = game;
		this.gameEngineConf = gameEngineConf;
		this.highSchoolChallenge = hscChallenge;
		this.recommenderApi = recommerderApiImpl;
	}

	public Result generate(Game game, GameEngineInfo gameEngineConf, HighSchoolChallenge highSchoolChallenge) {
		logger.info("Generate HSC challenge for gameId " + game.getGameId());
		final NextExecution nextChallengeExecution = new NextExecution(highSchoolChallenge, new ExecDate());
		if (nextChallengeExecution.isSuspended()) {
			logger.info("challenge will start in a suspension range, suspend assignment");
			return new ValidResult(true);
		} else {
			Map<String, String> gameEngineConfs = ConfigConverter.toGameEngineConfs(game, gameEngineConf);
			Map<String, List<eu.fbk.das.rs.challenges.Challenge>> creationRules = ConfigConverter
					.toCreationRules(highSchoolChallenge);
			Map<String, Object> challengeValues = ConfigConverter.toChallengeValues(nextChallengeExecution);

			List<ChallengeExpandedDTO> challenges = recommenderApi.createHSCChallenges(gameEngineConfs, creationRules, challengeValues);

			Set<String> distinctPlayers = new java.util.HashSet<String>();
			for (ChallengeExpandedDTO ch : challenges) {
				distinctPlayers.add(String.valueOf(ch.getInfo("pId")));
			}

			java.util.Iterator itr = distinctPlayers.iterator();
			while (itr.hasNext()) {
				System.out.print(itr.next() + ",\n");

			}

			System.out.println("distinct player count: " + distinctPlayers.size());

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