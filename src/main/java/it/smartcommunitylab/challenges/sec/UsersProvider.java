package it.smartcommunitylab.challenges.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import it.smartcommunitylab.challenges.model.AppContextProvider;
import it.smartcommunitylab.challenges.model.AuthUser;

@Component
public class UsersProvider {

	private Resource resource;

    private static final String DEFAULT_USERS_RESOURCE = "classpath:/users.yml";

    @Value("${security.usersFile:}")
    private String usersFilePath;

    @Autowired
    private AppContextProvider provider;

    private static final Logger logger = LogManager.getLogger(UsersProvider.class);
	

	@PostConstruct
	private void init() {

        resource = getUsersResource();

		Yaml yaml = new Yaml(new Constructor(UsersProvider.class));
		try {
			UsersProvider data = (UsersProvider) yaml.load(resource.getInputStream());
			this.users = data.users;
		} catch (IOException e) {
			logger.error("exception loading auth users resource");
			users = new ArrayList<AuthUser>();
		}

	}

    private Resource getUsersResource() {
        Resource users = null;
        boolean noExternalFound = StringUtils.isBlank(usersFilePath);
        if (!noExternalFound) {
            users = provider.getApplicationContext().getResource("file:" + usersFilePath);
            noExternalFound = !users.exists();
            if (noExternalFound) {
                logger.info("external users file {} not exist", usersFilePath);
            }
        }

        if (noExternalFound) {
            logger.info("not found any valid external users file...loading the default {}",
                    DEFAULT_USERS_RESOURCE);
            users = provider.getApplicationContext().getResource(DEFAULT_USERS_RESOURCE);
        } else {
            logger.info("loaded external users file {}", usersFilePath);
        }
        return users;
    }

	private List<AuthUser> users;

	public List<AuthUser> getUsers() {
		return users;
	}

	public void setUsers(List<AuthUser> users) {
		this.users = users;
	}

    public String getUsersFilePath() {
        return usersFilePath;
    }

    public void setUsersFilePath(String usersFilePath) {
        this.usersFilePath = usersFilePath;
    }

}
