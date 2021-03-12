package it.smartcommunitylab.challenges;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.fbk.das.api.RecommenderSystemAPI;
import eu.fbk.das.model.ChallengeExpandedDTO;
import eu.fbk.das.model.GroupExpandedDTO;
import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.SpecialSingleChallenge;
import it.smartcommunitylab.challenges.bean.StandardGroupChallenge;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class Challenges {

	private static Logger logger = LogManager.getLogger(Challenges.class);

	private GameEngineInfo gameEngineConf;
	private RecommenderSystemAPI recommenderApi;

	public Challenges(GameEngineInfo gameEngineConf) {
		this(gameEngineConf, new eu.fbk.das.api.RecommenderSystemImpl());
	}

	Challenges(GameEngineInfo gameEngineConf, RecommenderSystemAPI recommerderApiImpl) {
		this.gameEngineConf = gameEngineConf;
		this.recommenderApi = recommerderApiImpl;
	}

	public Result generate(Game game, StandardSingleChallenge standardSingleChallenges, ExecDate execDate) {
		logger.info("Generate standard single challenges.....");
		final NextExecution nextChallengeExecution = new NextExecution(standardSingleChallenges, execDate);
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
				challenges.stream().map(c -> new Challenge(c)).forEach(c -> {
					logger.debug("playerId:{}, instanceName:{}, model:{}, s:{}, e:{}, f:{}", c.playerId(),
							c.instanceName(), c.modelName(), c.start(), c.end(), c.data());
				});
			}
			logger.info("Created {} challenges for game {}", challenges.size(), game.getGameId());
			if (this.gameEngineConf.getAssign()) {
				challenges.forEach(challenge -> {
					// recommenderApi.assignSingleChallenge(gameEngineConfs, challenge);
				});
				logger.info("Assigned {} challenges for game {}", challenges.size(), game.getGameId());
			}
			return new ValidResult(true);
		}
	}

	public Result generate(Game game, StandardGroupChallenge standardGroupChallenges, ExecDate execDate) {
		logger.info("Generate group challenges.....");
		final NextExecution nextChallengeExecution = new NextExecution(standardGroupChallenges, execDate);
		Map<String, String> gameEngineConfs = ConfigConverter.toGameEngineConfs(game, gameEngineConf);
		Map<String, Object> challengeValues = ConfigConverter.toGroupChallengeValues(nextChallengeExecution);
		String playerSet = ConfigConverter.toPlayerSet(standardGroupChallenges.getPlayerSet());
		Map<String, String> rewards = ConfigConverter.toRewards(standardGroupChallenges.getReward());
		Set<String> modes = standardGroupChallenges.getSettings().getModes();
		final String assignmentType = standardGroupChallenges.getSettings().getModel();
		List<GroupExpandedDTO> challenges = recommenderApi.createStandardGroupChallenges(gameEngineConfs, modes,
				assignmentType, challengeValues, playerSet, rewards);
		logger.info("Created {} group challenges for game {}", challenges.size(), game.getGameId());
		if (logger.isDebugEnabled()) {
			challenges.forEach(c -> {
				logger.debug("model:{}, instanceName:{}, s:{}, e:{}, attendees: [{},{}]", c.getChallengeModelName(),
						c.getInstanceName(), c.getStart(), c.getEnd(), c.getAttendees().get(0).getPlayerId(),
						c.getAttendees().get(1).getPlayerId());
			});
		}
		if (this.gameEngineConf.getAssign()) {
			challenges.forEach(challenge -> {
				// recommenderApi.assignGroupChallenge(gameEngineConfs, challenge);
			});
			logger.info("Assigned {} group challenges for game {}", challenges.size(), game.getGameId());
		}
		return new ValidResult(true);
	}

	public Result generate(Game game, SpecialSingleChallenge specialSingleChallenges, ExecDate execDate) {
		final NextExecution nextChallengeExecution = new NextExecution(specialSingleChallenges, execDate);
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
				logger.debug("playerId:{}, instanceName:{}, model:{}, s:{}, e:{}, f:{}", c.playerId(),
						c.instanceName(), c.modelName(), c.start(), c.end(), c.data());
			});
		}
		logger.info("Created {} challenges of model {} for game {}", challenges.size(), model, game.getGameId());
		if (this.gameEngineConf.getAssign()) {
			challenges.forEach(challenge -> {
				// recommenderApi.assignSingleChallenge(gameEngineConfs, challenge);
			});
			logger.info("Assigned {} challenges of model {} for game {}", challenges.size(), model, game.getGameId());
		}
		return new ValidResult(true);
	}
}
