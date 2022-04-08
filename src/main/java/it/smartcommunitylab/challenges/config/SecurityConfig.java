package it.smartcommunitylab.challenges.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import it.smartcommunitylab.challenges.model.AuthUser;
import it.smartcommunitylab.challenges.sec.UsersProvider;

@Configuration
@EnableWebSecurity
@Profile({"sec"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LogManager.getLogger(SecurityConfig.class);


    @Autowired
    private UsersProvider usersProvider;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    	for (AuthUser user : usersProvider.getUsers()) {
            auth.inMemoryAuthentication().withUser(user.getUsername()).password("{noop}"+user.getPassword())
                    .roles(user.getRole());
            logger.info("Loaded auth user {}", user.getUsername());            
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // application never creates an http session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/api/**")
                .access("hasRole('ROLE_ADMIN')").and().httpBasic();

        http.logout().clearAuthentication(true).invalidateHttpSession(true);


        // disable csrf permits POST http call to DomainConsoleController
        // without using csrf token
        http.csrf().disable();

    }
}
