package eu.fbk.das.api.sample;

import java.util.Map;
import java.util.Set;


public class RecommenderSystemImpl implements RecommenderSystemAPI {

    @Override
    public boolean createSingleChallengeUnaTantum(Map<String, String> conf, String modelType,
            Map<String, Object> challengeValues, String playerSet, Map<String, String> rewards) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean createSingleChallengeWeekly(Map<String, String> conf, Set<String> modes,
            Map<String, String> creationRules, Map<String, Object> challengeValues,
            String playerSet, Map<String, String> rewards) {
        System.out.println("game-engine-conf: " + conf);
        System.out.println("modes: " + modes);
        System.out.println("creation-rules: " + creationRules);
        System.out.println("challenge-values: " + challengeValues);
        System.out.println("playerset: " + playerSet);
        System.out.println("reward: " + rewards);
        return true;
    }

    @Override
    public boolean createCoupleChallengeWeekly(Map<String, String> conf, Set<String> modelTypes,
            String assignmentType, Map<String, Object> challengeValues, String playerSet,
            Map<String, String> rewards) {
        // TODO Auto-generated method stub
        return true;
    }

}
