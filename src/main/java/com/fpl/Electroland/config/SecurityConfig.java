package com.fpl.Electroland.config;

import com.fpl.Electroland.common.Constanst;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers(Constanst.ADMIN_ALL).hasAuthority(Constanst.ROLE_ADMIN);
                    auth.anyRequest().permitAll();
                }
            ).formLogin(formLogin -> formLogin
                .loginPage(Constanst.URL_LOGIN)
                .loginProcessingUrl(Constanst.URL_PROCESS_LOGIN)
                .usernameParameter(Constanst.EMAIL_FIELD)
                .passwordParameter(Constanst.PASS_WORD_FIELD)
                .defaultSuccessUrl(Constanst.URL_ADMIN + Constanst.URL_INDEX, true)
                .permitAll()
            ).logout(httpSecurityLogoutConfigurer ->
                httpSecurityLogoutConfigurer.logoutUrl(Constanst.URL_LOGOUT)
                    .logoutSuccessUrl(Constanst.URL_LOGIN)
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies(Constanst.JSESSIONID)
                    .permitAll()
            ).exceptionHandling(exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer.accessDeniedPage(Constanst.ACCESS_DENIED)
            ).rememberMe(rememberMeConfigurer ->
                rememberMeConfigurer.key("electroland")
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(86400)
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
