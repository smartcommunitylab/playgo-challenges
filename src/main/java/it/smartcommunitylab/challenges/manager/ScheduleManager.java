package it.smartcommunitylab.challenges.manager;

import java.io.FileInputStream;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.challenges.StandardSingleChallengeTask;
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
	private static final String SCHEDULED_TASKS = "scheduledTasks";
	@Autowired
	private ScheduledAnnotationBeanPostProcessor postProcessor;

	private static final Logger logger = LogManager.getLogger(ScheduleManager.class);

	@PostConstruct
	public void init() {

		clean();

		String configPath = env.getProperty("config.challengeUrl");
		String schedulePath = env.getProperty("config.scheduleUrl");

		ConfigurationManager configurationManager = new YamlConfigurationManager();
		List<ChallengesSettings> challengesSettings;
		List<Schedule> scheduleSettings;
		try {
			challengesSettings = configurationManager.parseConfiguration(new FileInputStream(configPath));
			scheduleSettings = configurationManager.parseScheduleConfiguration(new FileInputStream(schedulePath));

			if (!scheduleSettings.isEmpty() && !challengesSettings.isEmpty()) {

				for (Schedule schedule : scheduleSettings) {
					GameEngineInfo gameEngineConf = new GameEngineInfo(env.getProperty("config.url"),
							env.getProperty("config.username"), env.getProperty("config.password"),
							schedule.getAssign(), env.getProperty("config.api_user"),
							env.getProperty("config.api_pass"));
					CronTrigger trigger = new CronTrigger(schedule.getExpression());
					scheduler.schedule(new StandardSingleChallengeTask(challengesSettings.get(0).getGame(),
							gameEngineConf, challengesSettings.get(0).getStandardSingleChallengeConfig()), trigger);
					logger.info("schedule imposed for gameId(" + schedule.getGameId() + ") - cron("
							+ schedule.getExpression() + ") - task(" + schedule.getTask() + ") - assign("
							+ schedule.getAssign() + ")");
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
