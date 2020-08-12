package it.smartcommunitylab.challenges;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.LevelStrategy;
import it.smartcommunitylab.challenges.bean.Reward;
import it.smartcommunitylab.challenges.bean.SpecialSettings;
import it.smartcommunitylab.challenges.bean.SpecialSingleChallenge;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

class ConfigConverter {

    public static Map<String, String> toGameEngineConfs(Game game, GameEngineInfo gameEngineConf) {
        Map<String, String> confs = new HashMap<>();
        confs.put("GAMEID", game.getGameId());
        confs.put("HOST", gameEngineConf.getUrl());
        confs.put("USER", gameEngineConf.getUsername());
        confs.put("PASS", gameEngineConf.getPassword());
        return confs;
    }

    public static Map<String, String> toCreationRules(
            StandardSingleChallenge standardSingleChallenges) {
        final List<LevelStrategy> creationRules = standardSingleChallenges.getLevelStrategies();
        Map<String, String> confs = new HashMap<>();
        creationRules.forEach(cr -> {
            confs.put(String.valueOf(cr.getLevel().getIndex()), cr.getStrategy());
        });
        // for other cases use the last one configured
        confs.put("other", creationRules.get(creationRules.size() - 1).getStrategy());
        return confs;
    }

    public static Map<String, Object> toChallengeValues(
            NextExecution nextChallengeExecution) {
        Map<String, Object> confs = new HashMap<>();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");
        confs.put("start", dateFormatter.format(nextChallengeExecution.getStart()));
        confs.put("duration", nextChallengeExecution.getDuration().toString().substring(1));
        confs.put("challengeWeek", nextChallengeExecution.getChallengeWeek());
        confs.put("exec", nextChallengeExecution.getExecutionDate());
        confs.put("hide", nextChallengeExecution.isHidden());
        return confs;
    }
    
    public static Map<String, Object> toGroupChallengeValues(
            NextExecution nextChallengeExecution) {
        Map<String, Object> confs = new HashMap<>();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");
        confs.put("start", dateFormatter.format(nextChallengeExecution.getStart()));
        confs.put("duration", nextChallengeExecution.getDuration().toString().substring(1));
        confs.put("challengeWeek", nextChallengeExecution.getChallengeWeek());
        confs.put("exec", nextChallengeExecution.getExecutionDate());
        return confs;
    }

    public static Map<String, Object> toSpecialChallengeValues(NextExecution nextChallengeExecution,
            SpecialSingleChallenge specialSingleChallenge) {
        Map<String, Object> confs = new HashMap<>();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");
        confs.put("start", dateFormatter.format(nextChallengeExecution.getStart()));
        confs.put("duration", nextChallengeExecution.getDuration().toString().substring(1));
        confs.put("hide", nextChallengeExecution.isHidden());
        final SpecialSettings settings = specialSingleChallenge.getSettings();
        final Map<String, Object> fields = settings.getFields();
        fields.entrySet().forEach(field -> {
            confs.put(field.getKey(), field.getValue());
        });
        return confs;
    }


    public static String toPlayerSet(Set<String> playerSet) {
        return playerSet.isEmpty() ? "all" : String.join(",", playerSet);
    }

    public static Set<String> toModes(StandardSingleChallenge standardSingleChallenges) {
        return standardSingleChallenges.getSettings().getModes();
    }

    public static Map<String, String> toRewards(Reward reward) {
        Map<String, String> confs = new HashMap<>();
        confs.put("scoreType", reward.getScoreName());
        // reward type is null for groupChallenges
        final String rewardTypeAsString =
                reward.getType() != null ? reward.getType().toString().toLowerCase() : null;
        confs.put("calcType", rewardTypeAsString);
        confs.put("calcValue", String.valueOf(reward.getValue()));
        confs.put("maxValue", reward.getMaxValue().map(m -> String.valueOf(m)).orElse(null));
        return confs;
    }
}
