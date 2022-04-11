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

@RestController
@RequestMapping(value = "/api")
public class ChallengeController {

	private static final Logger logger = LogManager.getLogger(ChallengeController.class);
	
	@Autowired
	private ChallengeManager challengeSrv;

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

}
