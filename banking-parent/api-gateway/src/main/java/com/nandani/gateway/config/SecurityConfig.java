package com.nandani.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		
		return http.csrf(
				csrf -> csrf.disable()
				)
		.authorizeExchange(
				auth-> auth.pathMatchers("/public/**")
				.permitAll().pathMatchers("/auth/admin/**").hasRole("ADMIN")
				.anyExchange().authenticated()
				)
		.oauth2ResourceServer(
				oauth -> oauth.jwt(Customizer.withDefaults())
				
				).build();
		
		
		
	}
	     

}
