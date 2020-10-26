/*
 * package com.bs.security.basic;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.http.HttpMethod; import
 * org.springframework.security.authentication.AuthenticationManager; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.crypto.password.NoOpPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder;
 * 
 * @SuppressWarnings("deprecation")
 * 
 * @Configuration public class BrokerageServiceSecurityWithBasicAuthentication
 * extends WebSecurityConfigurerAdapter {
 * 
 * @Autowired UserDetailsService userDetailsService;
 * 
 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
 * Exception { auth.userDetailsService(userDetailsService); }
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception {
 * //The issue is that the HTTP request from the your website and the request
 * from the evil website are exactly the same.(i.e. if the request is forged)
 * //This means there is no way to reject requests coming from the evil website
 * and allow requests coming from the your website. // In that case csrf(Cross
 * Site Request Forgery) is used
 * 
 * http.csrf().disable() .authorizeRequests()
 * .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
 * .anyRequest().authenticated() .and().httpBasic(); //.formLogin();
 * 
 * 
 * According to a blog post, a line needs to be added to the configure method of
 * the SecurityConfig class if you have the spring-boot-starter-security
 * dependency in your project, otherwise you will see an empty page after
 * logging into the H2 console:
 * 
 * http.headers().frameOptions().disable();
 * 
 * }
 * 
 * 
 * @Bean public PasswordEncoder getPasswordEncoder() { return
 * NoOpPasswordEncoder.getInstance(); }
 * 
 * //exposes authentication manager bean to configure (authentication manager
 * builder)
 * 
 * @Override
 * 
 * @Bean public AuthenticationManager authenticationManagerBean() throws
 * Exception { return super.authenticationManagerBean(); } }
 */