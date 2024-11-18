package com.webapp.project.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.webapp.project.Services.ClientService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Bean
        public UserDetailsService userDetailsService() {
                // Return your custom user service (ClientService)
                return new ClientService();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/register") // Disable CSRF protection for
                                                                                      // /register
                                )
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/").permitAll()
                                                .requestMatchers("/register").permitAll()
                                                .requestMatchers("/login").permitAll()
                                                .anyRequest().authenticated()
                                                //.anyRequest().permitAll()

                                )
                                .formLogin(form -> form
                                                .defaultSuccessUrl("/", true))
                                .logout(config -> config.logoutSuccessUrl("/"))
                                .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}
