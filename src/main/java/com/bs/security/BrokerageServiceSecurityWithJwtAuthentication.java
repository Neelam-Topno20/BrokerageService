


package com.bs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("deprecation")

@Configuration
public class BrokerageServiceSecurityWithJwtAuthentication extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override 
  protected void configure(HttpSecurity http) throws Exception {
  //The issue is that the HTTP request from the your website and the request
  //from the evil website are exactly the same.(i.e. if the request is forged)
  //This means there is no way to reject requests coming from the evil website
  //and allow requests coming from the your website. // In that case csrf(Cross
  //Site Request Forgery) is used
		
		
		
		
  
  http.csrf().disable().authorizeRequests()
  .antMatchers("/authenticate").permitAll()
  .antMatchers(HttpMethod.OPTIONS).permitAll()
  .antMatchers("/h2-console/**").permitAll()
  .antMatchers(HttpMethod.GET, "users/all").hasRole("ADMIN")
  .antMatchers(HttpMethod.POST, "/users").permitAll()
  .antMatchers(HttpMethod.DELETE, "/users").hasRole("ADMIN")
  .antMatchers(HttpMethod.GET, "/trades").hasRole("ADMIN")
  .antMatchers(HttpMethod.DELETE, "/erase").hasRole("ADMIN")
  .antMatchers("/**").hasAnyRole("USER","ADMIN")
  .anyRequest().authenticated();
  
		/*
		 * .and().sessionManagement()
		 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 */
  
  
  
		/*
		 * http.authorizeRequests()
		 * .antMatchers("/authenticate","/trades/{id}","/health",
		 * "/trades/users/{userId}").permitAll()
		 * .antMatchers("/h2-console/**").permitAll()
		 * .antMatchers("/**").hasAnyRole("USER","ADMIN") .and() //.formLogin();
		 * .httpBasic();
		 */
  //.anyRequest().authenticated().and().sessionManagement()
  //.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  
  //setting for viewing H2 console:
  
  http.headers().frameOptions().disable();
  
  http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
  
  }

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	// exposes authentication manager bean to configure (authentication manager
	// builder)

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
