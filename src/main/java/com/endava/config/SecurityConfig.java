package com.endava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
				.antMatchers("/actors").hasRole("ADMIN")
				//.antMatchers("").hasAnyRole("ADMIN", "USER")
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
				
				.and()
					.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error")
					.usernameParameter("username")
					.passwordParameter("password")
				.and()
					.logout()
					.logoutSuccessUrl("/login?logout")
				.and()
					.exceptionHandling().accessDeniedPage("/403")
				.and()
					.csrf().disable();
		// @formatter:on
	}

}
