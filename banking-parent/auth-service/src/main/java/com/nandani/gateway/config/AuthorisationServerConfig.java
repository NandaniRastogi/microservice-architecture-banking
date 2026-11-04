package com.nandani.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class AuthorisationServerConfig {

	@Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Yahan aap apna custom username aur password set kar sakte hain
        UserDetails admin = User.builder()
                .username("configadmin")
                .password(passwordEncoder.encode("configadmin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    @Order(-1) // Authorization Server ki priority high rakhne ke liye
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // 1. Authorization Server ka configurer create karo
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        // 2. HttpSecurity par authorization server ke rules apply karo
        http.formLogin(Customizer.withDefaults())
            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .with(authorizationServerConfigurer, (authorizationServer) ->
                authorizationServer
                    .oidc(oidc -> {}) // OpenID Connect support enable karne ke liye
            )
            // Agar unauthorized user aaye toh login page par redirect karo
            .exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(
                    new LoginUrlAuthenticationEntryPoint("/login")
                )
            )
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
   public  PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        // Yahan Auth-Service ya Keycloak ka issuer-uri ya JWK set URI dena hota hai
        // Jisse token ki signature verify ho sake.
        // Example ke taur par agar aapka auth-service http://localhost:9000 par chal raha hai:
        String jwkSetUri = "http://localhost:9000/oauth2/jwks"; // Ya apna Auth Server ka JWK URI dalein

        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}