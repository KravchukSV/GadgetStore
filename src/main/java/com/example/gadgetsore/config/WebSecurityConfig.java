package com.example.gadgetsore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public WebSecurityConfig(AuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/**", "/js/**",
                                "/smartphones/**", "/smartphone/**",
                                "/error**", "login**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> {
                    oauth2Login.successHandler(customAuthenticationSuccessHandler);
                });
                //.oauth2Login(Customizer.withDefaults())

        http.logout(l -> l
                .logoutSuccessUrl("/").permitAll()
        );
        return http.build();
    }
}