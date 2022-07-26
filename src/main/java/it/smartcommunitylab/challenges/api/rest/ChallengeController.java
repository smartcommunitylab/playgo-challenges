package it.smartcommunitylab.challenges.api.rest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import it.smartcommunitylab.challenges.bean.ConfigurationStub;
import it.smartcommunitylab.challenges.manager.ChallengeManager;
import it.smartcommunitylab.challenges.manager.ScheduleManager;

@RestController
@RequestMapping(value = "/api")
public class ChallengeController {

	private static final Logger logger = LogManager.getLogger(ChallengeController.class);
	
	@Autowired
	private ChallengeManager challengeSrv;
	@Autowired
	private ScheduleManager scheduleManager;

	@RequestMapping(method = RequestMethod.POST, value = "/generate")
	@Operation(summary = "generate challenge")
	public void generate(
			@RequestParam(required = false) String execDate, 
			@RequestParam(required = false) String task,
			@RequestParam(required = false) String assign,
			@RequestBody List<ConfigurationStub> gameConfigs) {
		logger.info("/api/generate - invoked automatic challenge generation task");
		challengeSrv.invokeGenerator(gameConfigs, execDate, task, assign);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/reschedule")
	@Operation(summary = "reschedule challenge")
	public void reschedule() {
		logger.info("/api/reschedule - force reset of scheduling tasks");
		scheduleManager.init();
	}
}
