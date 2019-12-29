package com.techprimers.security.springsecurityauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService customUserDetailsService;
	@Autowired
	@Qualifier("bcryptEncoder")
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("encode")
	private PasswordEncoder encode;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.requestMatchers().antMatchers("/login", "/oauth/authorize").and().authorizeRequests().anyRequest()
				.authenticated().and().formLogin().permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.parentAuthenticationManager(authenticationManager).userDetailsService(customUserDetailsService)
				.passwordEncoder(encode);
	}

	@Bean("encode")
	public PasswordEncoder getPassEncoder() {
		return new PasswordEncoder() {
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				if (passwordEncoder.matches(rawPassword, encodedPassword)) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public String encode(CharSequence rawPassword) {
				return passwordEncoder.encode(rawPassword);
			}
		};
	}
	
	
}
