package it.smartcommunitylab.challenges;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.fbk.das.api.RecommenderSystemAPI;
import eu.fbk.das.model.GroupExpandedDTO;
import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.StandardGroupChallenge;

public class StandardGroupChallengeTask implements Runnable {

	private static final Logger logger = LogManager.getLogger(StandardGroupChallengeTask.class);

	private GameEngineInfo gameEngineConf;
	private RecommenderSystemAPI recommenderApi;
	private Game game;
	private StandardGroupChallenge standardGroupChallenge;

	@Override
	public void run() {
		System.out.println(System.currentTimeMillis() + " Runnable Task (StandardGroupChallengeTask)" + " on thread "
				+ Thread.currentThread().getName());
		this.generate(game, gameEngineConf, standardGroupChallenge);
	}

	public StandardGroupChallengeTask(Game game, GameEngineInfo gameEngineConf,
			StandardGroupChallenge standardGroupChallenges) {
		this(game, gameEngineConf, standardGroupChallenges, new eu.fbk.das.api.RecommenderSystemImpl());
	}

	StandardGroupChallengeTask(Game game, GameEngineInfo gameEngineConf, StandardGroupChallenge standardGroupChallenges,
			RecommenderSystemAPI recommerderApiImpl) {
		this.game = game;
		this.gameEngineConf = gameEngineConf;
		this.standardGroupChallenge = standardGroupChallenges;
		this.recommenderApi = recommerderApiImpl;
	}

	public Result generate(Game game, GameEngineInfo gameEngineConf, StandardGroupChallenge standardGroupChallenges) {
		logger.info("Generate group challenge for gameId {}", game.getGameId());
		final NextExecution nextChallengeExecution = new NextExecution(standardGroupChallenges, new ExecDate());
		Map<String, String> gameEngineConfs = ConfigConverter.toGameEngineConfs(game, gameEngineConf);
		Map<String, Object> challengeValues = ConfigConverter.toGroupChallengeValues(nextChallengeExecution);
		challengeValues.put("minLevel", standardGroupChallenge.getSettings().getMinLevel());
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
		if (Boolean.TRUE.equals(this.gameEngineConf.getAssign())) {
			challenges.forEach(challenge -> {
				recommenderApi.assignGroupChallenge(gameEngineConfs, challenge);
			});
			logger.info("Assigned {} group challenges for game {}", challenges.size(), game.getGameId());
		}
		return new ValidResult(true);
	}

}