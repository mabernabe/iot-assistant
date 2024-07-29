package com.iotassistant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class IoTAssistantSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static String LOGIN_PATH = "/app/login";
	
	private static String LOGIN_PAGE = LOGIN_PATH + "/login.html"; 
	
	@Value("${login.username}")
	private String username;
	
	@Value("${login.password}")
	private String password;
	
	 @Bean
	 public AuthenticationManager noopAuthenticationManager() {
		 return new AuthenticationManager() {
			 @Override
			 public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				 throw new AuthenticationServiceException("Authentication is disabled");
			 }
		 }; 
	 }
	
 	@Override
 	protected void configure(HttpSecurity http) throws Exception {
 		 http
         .authorizeRequests()
         .antMatchers("/app/assets/**").permitAll()
         .antMatchers("/app/libraries/**").permitAll()
         .anyRequest().authenticated()
         .and()
 				.formLogin() 
 				.loginPage(LOGIN_PAGE)
 				.loginProcessingUrl("/login")
 				.defaultSuccessUrl("/app/dashboard/dashboard.html")
 				.usernameParameter("username")
 				.passwordParameter("password")
 				.failureUrl(LOGIN_PAGE + "?error")
 				.permitAll()
 				.and()
 	            .logout()
 	            .logoutUrl("/logout")
 	            .invalidateHttpSession(true)
 	            .logoutSuccessUrl(LOGIN_PAGE)
 	            .deleteCookies("JSESSIONID")
 	        .and()
 	            .csrf().disable();
 	}

 	@Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().withUser(username)
                .password(password).roles("ADMIN");
    }
 	
 	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


	
	

}
