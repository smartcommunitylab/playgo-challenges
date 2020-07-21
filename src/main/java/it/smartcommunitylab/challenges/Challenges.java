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
import it.smartcommunitylab.challenges.bean.StandardGroupChallenge;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class Challenges {

    private static Logger logger = LogManager.getLogger(Challenges.class);

    private GameEngineInfo gameEngineConf;
    private RecommenderSystemAPI recommenderApi;

    public Challenges(GameEngineInfo gameEngineConf) {
        this.gameEngineConf = gameEngineConf;
        // use eu.fbk.das.api.sample to test without challenge-gen integration
        recommenderApi = new eu.fbk.das.api.RecommenderSystemImpl();
    }

    Challenges(GameEngineInfo gameEngineConf, RecommenderSystemAPI recommerderApiImpl) {
        this.gameEngineConf = gameEngineConf;
        this.recommenderApi = recommerderApiImpl;
    }

    public Result assign(Game game, StandardSingleChallenge standardSingleChallenges) {
        final NextExecution nextChallengeExecution = new NextExecution(standardSingleChallenges);
        if (nextChallengeExecution.isSuspended()) {
            logger.info("challenge will start in a suspension range, suspend assignment");
            return new ValidResult(true);
        } else {
            Map<String, String> gameEngineConfs =
                    ConfigConverter.toGameEngineConfs(game, gameEngineConf);
            Map<String, String> creationRules =
                    ConfigConverter.toCreationRules(standardSingleChallenges);
            Map<String, Object> challengeValues =
                    ConfigConverter.toChallengeValues(nextChallengeExecution);
            String playerSet = ConfigConverter.toPlayerSet(standardSingleChallenges.getPlayerSet());
            Map<String, String> rewards =
                    ConfigConverter.toRewards(standardSingleChallenges.getReward());
            Set<String> modes = standardSingleChallenges.getSettings().getModes();

            List<ChallengeExpandedDTO> challenges =
                    recommenderApi.createSingleChallengeWeekly(gameEngineConfs, modes,
                            creationRules,
                            challengeValues, playerSet, rewards);
            if (logger.isDebugEnabled()) {
                challenges.forEach(c -> {
                        logger.debug("model:{}, s:{}, e:{}, f:{}", c.getModelName(),
                                c.getStart(), c.getEnd(), c.getData());
                    });
            }
            logger.info("Created {} challenges for game {}", challenges.size(), game.getGameId());
            challenges.forEach(challenge -> {
                recommenderApi.assignSingleChallenge(gameEngineConfs, challenge);
            });
            logger.info("Assigned {} challenges for game {}", challenges.size(), game.getGameId());
            return new ValidResult(true);
        }
    }

    public Result assign(Game game, StandardGroupChallenge standardGroupChallenges) {
        logger.info("Assign group challenges.....");
        final NextExecution nextChallengeExecution = new NextExecution(standardGroupChallenges);
        Map<String, String> gameEngineConfs =
                ConfigConverter.toGameEngineConfs(game, gameEngineConf);
        Map<String, Object> challengeValues =
                ConfigConverter.toGroupChallengeValues(nextChallengeExecution);
        String playerSet = ConfigConverter.toPlayerSet(standardGroupChallenges.getPlayerSet());
        Map<String, String> rewards =
                ConfigConverter.toRewards(standardGroupChallenges.getReward());
        Set<String> modes = standardGroupChallenges.getSettings().getModes();
        final String assignmentType = standardGroupChallenges.getSettings().getModel();
        List<GroupExpandedDTO> challenges = recommenderApi.createCoupleChallengeWeekly(
                gameEngineConfs, modes, assignmentType, challengeValues, playerSet, rewards);
        logger.info("Created {} group challenges for game {}", challenges.size(), game.getGameId());
        if (logger.isDebugEnabled()) {
            challenges.forEach(c -> logger.debug(c));
        }
        challenges.forEach(challenge -> {
            recommenderApi.assignGroupChallenge(gameEngineConfs, challenge);
        });
        logger.info("Assigned {} group challenges for game {}", challenges.size(),
                game.getGameId());
        return new ValidResult(true);
    }
}
