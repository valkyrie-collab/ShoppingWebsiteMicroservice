package com.valkyrie.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private TokenFilter filter;
    @Autowired
    private void setFilter(TokenFilter filter) {this.filter = filter;}

    // private CustomUserDetailsService service;
    // @Autowired
    // private void setService(CustomUserDetailsService service) {
    //     this.service = service;
    // }

    @Bean
    public BCryptPasswordEncoder encoder() {return new BCryptPasswordEncoder(12);}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Throwable {
        return security.csrf(c -> c.disable()).authorizeHttpRequests(
            a -> a.requestMatchers("/user/sign-in", "/user/log-in").permitAll().anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults()).sessionManagement(
            s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
    }

    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    //     return new CustomAuthenticationProvider(encoder(), service);
    // }

    // @Bean
    // public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    //     return config.getAuthenticationManager();
    // }
}
