package com.HTW.StudentFaceRecognition.Config;

import com.HTW.StudentFaceRecognition.Security.Filter.CustomRequestHeaderTokenFilter;
import com.HTW.StudentFaceRecognition.Security.Filter.LogInFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {
    @Autowired
    LogInFilter logInFilter;
    @Autowired
    CustomRequestHeaderTokenFilter customRequestHeaderTokenFilter;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((requests) -> requests.requestMatchers("/signin","/images/**").permitAll()
                .anyRequest().authenticated());
        //.formLogin(Customizer.withDefaults())
        //.httpBasic(Customizer.withDefaults());
        http.securityContext((context) -> context
                        .requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Token")); // Nếu bạn muốn client có thể đọc 'token' từ response

                        config.setMaxAge(3600L);
                        return config;
                    }
                }));
        http.addFilterBefore(customRequestHeaderTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(logInFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

}
