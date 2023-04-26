package dev.evgeni.personsapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import dev.evgeni.personsapi.web.JwtFilter;

@EnableWebSecurity
@Configuration
public class SecurityFilterChainConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests( auth -> {
                    auth
                    .requestMatchers(HttpMethod.GET, "/photos").permitAll()
                    .requestMatchers(HttpMethod.GET, 
                        "/swagger-ui", 
                                    "/swagger-ui/*", 
                                    "/v3/api-docs/swagger-config", 
                                    "/v3/api-docs").permitAll()
                    .requestMatchers(HttpMethod.POST, "/token").permitAll()
                    .requestMatchers(HttpMethod.POST, "/persons").hasRole("ADMIN")
                    .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
    
}
