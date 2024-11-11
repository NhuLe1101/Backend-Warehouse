package com.backend.warehouse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.warehouse.security.jwt.AuthEntryPointJwt;
import com.backend.warehouse.security.jwt.AuthTokenFilter;
import com.backend.warehouse.service.UserDetailsServiceImpl;


@Configuration
@EnableMethodSecurity
// (securedEnabled = true,
//  jsr250Enabled  = true,
//  prePostEnabled = true ) // by default
public class WebSecurityConfig { 
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> 
	          auth.requestMatchers("/api/booking/**").permitAll()
	          	  .requestMatchers("/api/jasper/**").permitAll()
	          	  .requestMatchers("/api/product/**").permitAll()
	              .requestMatchers("/api/compartments/**").permitAll()
	          	  .requestMatchers("/api/shelf/**").permitAll()
	          	  .requestMatchers("/api/warehouse/**").permitAll()
	          	  .requestMatchers("/api/notifications/**").permitAll()
	              .requestMatchers("/api/auth/**").permitAll()
	              .requestMatchers("/api/test/**").permitAll()
	              .requestMatchers("/ws/**", "/topic/**", "/app/**").permitAll() 
	              .anyRequest().authenticated()
	        );

	    http.authenticationProvider(authenticationProvider());

	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	}

}