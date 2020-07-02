package it.smartcommunitylab.challenges;

import java.util.Map;
import java.util.Set;

import eu.fbk.das.api.RecommenderSystemAPI;
import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class Challenges {
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
            System.out.println("challenge will start in a suspension range, suspend assignment");
            return new ValidResult(true);
        } else {
            Map<String, String> gameEngineConfs =
                    ConfigConverter.toGameEngineConfs(game, gameEngineConf);
            Map<String, String> creationRules =
                    ConfigConverter.toCreationRules(standardSingleChallenges);
            Map<String, Object> challengeValues =
                    ConfigConverter.toChallengeValues(nextChallengeExecution);
            String playerSet = ConfigConverter.toPlayerSet(standardSingleChallenges);
            Map<String, String> rewards = ConfigConverter.toRewards(standardSingleChallenges);
            Set<String> modes = ConfigConverter.toModes(standardSingleChallenges);

            recommenderApi.createSingleChallengeWeekly(gameEngineConfs, modes, creationRules,
                    challengeValues, playerSet, rewards).forEach(c -> {
                        System.out.printf("model: %s, s:%s, e:%s, f:%s\n", c.getModelName(),
                                c.getStart(), c.getEnd(), c.getData());
                    });
            return new ValidResult(true);
        }
    }
}
