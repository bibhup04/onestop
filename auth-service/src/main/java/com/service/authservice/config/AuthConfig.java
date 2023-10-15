package com.service.authservice.config;


import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;


import com.service.authservice.CorsConfig;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfig))
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/auth/register", "/auth/token", "/auth/userId").permitAll()
                .anyRequest().authenticated())
                .logout(withDefaults())
                .httpBasic(withDefaults())
                // .oauth2ResourceServer(
                // oauth2ResourceServer -> oauth2ResourceServer.jwt(jwt -> 
                //                                                     jwt.decoder(jwtDecoder())))
                // .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // .exceptionHandling((exceptions) -> exceptions
                // .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                // .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                // );
                .build();
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    //     http
    //         .csrf(csrf -> csrf.disable())
    //         .cors(cors -> cors.configurationSource(corsConfig))
    //         .authorizeHttpRequests((requests) -> requests
    //         .requestMatchers("/register", "/api/auth/token", "/api/auth/social/token","/v2/api-docs","/add.html").permitAll()
    //         // .requestMatchers("/api/cycles/add").hasRole("ADMIN")
    //         .requestMatchers("/api/cycles/add","/api/cycles/all-Purchases").hasAuthority("SCOPE_ROLE_ADMIN")
    //         .anyRequest().authenticated())
    //         .logout(withDefaults())
    //         .httpBasic(withDefaults())
    //         //.formLogin(withDefaults())
    //         .oauth2ResourceServer(
    //             oauth2ResourceServer -> oauth2ResourceServer.jwt(jwt -> 
    //                                                                 jwt.decoder(jwtDecoder())))
    //         .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //         .exceptionHandling((exceptions) -> exceptions
    //         .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
    //         .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
    // );
        
    //     return http.build();
    // }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



}

