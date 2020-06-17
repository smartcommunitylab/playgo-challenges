package it.smartcommunitylab.challenges;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.smartcommunitylab.challenges.bean.Game;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;
import it.smartcommunitylab.challenges.bean.LevelStrategy;
import it.smartcommunitylab.challenges.bean.Reward;
import it.smartcommunitylab.challenges.bean.Settings;
import it.smartcommunitylab.challenges.bean.StandardSingleChallenge;

class ConfigConverter {

    public static Map<String, String> toGameEngineConfs(Game game, GameEngineInfo gameEngineConf) {
        Map<String, String> confs = new HashMap<>();
        confs.put("gameId", game.getGameId());
        confs.put("host", gameEngineConf.getUrl());
        confs.put("user", gameEngineConf.getUsername());
        confs.put("pass", gameEngineConf.getPassword());
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
            StandardSingleChallenge standardSingleChallenges) {
        final TimeHelper timeHelper = new TimeHelper();
        Map<String, Object> confs = new HashMap<>();
        final Settings challengeConfig = standardSingleChallenges.getSettings();
        final Date nextStart = timeHelper.calculateNextStart(challengeConfig.getStart(),
                challengeConfig.getDuration());
        final Date end = timeHelper.calculateEnd(nextStart, challengeConfig.getDuration());
        confs.put("start", nextStart);
        confs.put("end", end);
        confs.put("challengeWeek", new SimpleDateFormat("yyyy-MM-dd").format(nextStart));
        confs.put("execDate", System.currentTimeMillis());
        confs.put("hide", standardSingleChallenges.getSettings().isHide());
        return confs;
    }


    public static String toPlayerSet(StandardSingleChallenge standardSingleChallenges) {
        final Set<String> playerSet = standardSingleChallenges.getPlayerSet();
        return playerSet.isEmpty() ? "all" : String.join(",", playerSet);
    }

    public static Set<String> toModes(StandardSingleChallenge standardSingleChallenges) {
        return standardSingleChallenges.getSettings().getModes();
    }

    public static Map<String, String> toRewards(StandardSingleChallenge standardSingleChallenges) {
        final Reward reward = standardSingleChallenges.getReward();
        Map<String, String> confs = new HashMap<>();
        confs.put("scoreType", reward.getScoreName());
        confs.put("calcType", reward.getType().toString().toLowerCase());
        confs.put("calcValue", String.valueOf(reward.getValue()));
        confs.put("maxValue", reward.getMaxValue().map(m -> String.valueOf(m)).orElse(null));
        return confs;
    }

}
