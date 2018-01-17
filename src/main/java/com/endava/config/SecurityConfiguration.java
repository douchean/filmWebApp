package com.endava.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().and().authorizeRequests().antMatchers(HttpMethod.POST, "/films").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/films/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/films/{id}").authenticated().antMatchers(HttpMethod.POST, "/directors")
				.hasRole("ADMIN").antMatchers(HttpMethod.DELETE, "/directors/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/directors/{id}").authenticated().antMatchers(HttpMethod.POST, "/actors")
				.hasRole("ADMIN").antMatchers(HttpMethod.DELETE, "/actors/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/actors/{id}").authenticated()

				.anyRequest().permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery(" select username, role from user_roles where username=?");
	}

}
