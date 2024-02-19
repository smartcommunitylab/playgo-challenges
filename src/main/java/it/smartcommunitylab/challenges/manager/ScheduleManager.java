package it.smartcommunitylab.challenges.manager;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.challenges.HSCChallengeTask;
import it.smartcommunitylab.challenges.SpecialSingleChallengeTask;
import it.smartcommunitylab.challenges.StandardGroupChallengeTask;
import it.smartcommunitylab.challenges.StandardSingleChallengeTask;
import it.smartcommunitylab.challenges.app.Tasker;
import it.smartcommunitylab.challenges.app.configuration.ChallengesSettings;
import it.smartcommunitylab.challenges.app.configuration.ConfigurationManager;
import it.smartcommunitylab.challenges.app.configuration.Schedule;
import it.smartcommunitylab.challenges.app.configuration.YamlConfigurationManager;
import it.smartcommunitylab.challenges.bean.GameEngineInfo;

@Component
public class ScheduleManager {

	@Autowired
	private Environment env;
	@Autowired
	private ThreadPoolTaskScheduler scheduler;
	private List<ChallengesSettings> challengesSettings;
	@Value("${config.challengeUrl}")
	private String configPath;	
	@Value("${config.scheduleUrl}")
	private String schedulePath;	

	private static final Logger logger = LogManager.getLogger(ScheduleManager.class);
		
	@PostConstruct
	public void init() {

		clean();
		
		logger.info("config.challengeUrl->" + configPath);
		logger.info("config.scheduleUrl->" + schedulePath);
		logger.info("config.url->" + env.getProperty("config.url"));
	
		ConfigurationManager configurationManager = new YamlConfigurationManager();
		List<Schedule> scheduleSettings;
		try {
			challengesSettings = configurationManager.parseConfiguration(new FileInputStream(configPath));
			scheduleSettings = configurationManager.parseScheduleConfiguration(new FileInputStream(schedulePath));

			if (!scheduleSettings.isEmpty() && !challengesSettings.isEmpty()) {
				for (Schedule schedule : scheduleSettings) {
					Optional<ChallengesSettings> gcs = challengesSettings.stream()
							.filter(gc -> gc.getGame().getGameId().equals(schedule.getGameId())).findFirst();
					if (!gcs.isEmpty()) {
						ChallengesSettings gc = gcs.get();
						GameEngineInfo gameEngineConf = new GameEngineInfo(
								env.getProperty("config.url"),
								schedule.getAssign(),
								env.getProperty("config.api_user"),
								env.getProperty("config.api_pass"),
								env.getProperty("config.postgresUrl"));
						CronTrigger trigger = new CronTrigger(schedule.getExpression());
						SimpleTriggerContext triggerContext = new SimpleTriggerContext();
						triggerContext.update(null, null, new Date());
						Date nextFireAt = trigger.nextExecutionTime(triggerContext);
						if (schedule.getTask().equalsIgnoreCase(Tasker.SPECIAL_SINGLE_OPTION)) {
							gc.getSpecialSingleChallengeConfig().forEach(special -> 
							scheduler.schedule(new SpecialSingleChallengeTask(gc.getGame(), gameEngineConf, special),
									trigger));							
						} else if (schedule.getTask().equalsIgnoreCase(Tasker.STANDARD_SINGLE_OPTION)) {
							scheduler.schedule(new StandardSingleChallengeTask(gc.getGame(), gameEngineConf,
									gc.getStandardSingleChallengeConfig()), trigger);
						} else if (schedule.getTask().equalsIgnoreCase(Tasker.STANDARD_GROUP_OPTION)) {
							scheduler.schedule(new StandardGroupChallengeTask(gc.getGame(), gameEngineConf,
									gc.getStandardGroupChallengeConfig()), trigger);
						} else if (schedule.getTask().equalsIgnoreCase(Tasker.HSC_CHALLENGE_OPTION)) {
							scheduler.schedule(new HSCChallengeTask(gc.getGame(), gameEngineConf,
									gc.getHighSchoolChallengeConfig()), trigger);
						}
						logger.info("schedule imposed for gameId(" + schedule.getGameId() + ") - cron("
								+ schedule.getExpression() + ") - task(" + schedule.getTask() + ") - assign("
								+ schedule.getAssign() + ") - fireTime " + nextFireAt);
					} else {
						logger.error("scheduling failed - missing schedule mapped game configuration for id - " + schedule.getGameId());
					}

				}
			} else {
				logger.error("scheduling failed - missing configuration file");
			}
		} catch (Exception e) {
			logger.error("scheduling failed - " + e.getMessage());
		}
	}

	private void clean() {
		scheduler.shutdown();
		scheduler.initialize();
	}
	
}
