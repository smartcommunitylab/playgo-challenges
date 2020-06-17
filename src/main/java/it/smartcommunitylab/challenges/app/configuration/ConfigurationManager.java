package it.smartcommunitylab.challenges.app.configuration;

import java.io.InputStream;
import java.util.List;

public interface ConfigurationManager {
    List<ChallengesSettings> parseConfiguration(InputStream source);

}
