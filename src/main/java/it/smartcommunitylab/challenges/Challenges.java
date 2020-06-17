package it.smartcommunitylab.challenges;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import eu.fbk.das.api.RecommenderSystemAPI;
import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

public class Challenges {
    private GameEngineInfo gameEngineConf;
    private RecommenderSystemAPI recommenderApi;
    private TimeHelper timeHelper;


    public Challenges(GameEngineInfo gameEngineConf) {
        this.gameEngineConf = gameEngineConf;
        timeHelper = new TimeHelper();
        // use eu.fbk.das.api.sample to test without challenge-gen integration
        recommenderApi = new eu.fbk.das.api.RecommenderSystemImpl();
    }

    Challenges(GameEngineInfo gameEngineConf, RecommenderSystemAPI recommerderApiImpl) {
        this.gameEngineConf = gameEngineConf;
        this.recommenderApi = recommerderApiImpl;
    }

    public Result assign(Game game, StandardSingleChallenge standardSingleChallenges) {
        Map<String, String> gameEngineConfs =
                ConfigConverter.toGameEngineConfs(game, gameEngineConf);
        Map<String, String> creationRules =
                ConfigConverter.toCreationRules(standardSingleChallenges);
        Map<String, Object> challengeValues =
                ConfigConverter.toChallengeValues(standardSingleChallenges);
        String playerSet = ConfigConverter.toPlayerSet(standardSingleChallenges);
        Map<String, String> rewards = ConfigConverter.toRewards(standardSingleChallenges);
        Set<String> modes = ConfigConverter.toModes(standardSingleChallenges);

        // check for suspensions
        final Date startDate = (Date) challengeValues.get("start");
        boolean suspend = timeHelper.shouldBeSospended(startDate, standardSingleChallenges);
        if (suspend) {
            System.out.println("challenge will start in a suspension range, suspend assignment");
            return new ValidResult(true);
        } else {
            boolean result = recommenderApi.createSingleChallengeWeekly(gameEngineConfs, modes,
                    creationRules, challengeValues, playerSet, rewards);
            return new ValidResult(result);
        }
    }

}
