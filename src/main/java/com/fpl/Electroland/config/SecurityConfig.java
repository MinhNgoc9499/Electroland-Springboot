package com.fpl.Electroland.config;

import com.fpl.Electroland.dao.NhanVienDAO;
import com.fpl.Electroland.model.NhanVien;
import com.fpl.Electroland.model.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    WebSecurityCustomizer configureWebSecurity() {
        return (web) -> web.ignoring()
            .requestMatchers("/images/**", "/js/**", "/css/**", "/webjars/**", "/JS/images/**", "/JS/**",
                "/JS/css/**", "/JS/webjars/**");
    }

    private final NhanVienDAO nhanvienDao;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/signin", "/dang-ky", "index").permitAll()
//                    .requestMatchers("/index").hasAuthority("ADMIN")
//                    .requestMatchers("/users/**", "/apps/**").hasAuthority("ADMIN")
//                    .requestMatchers("/user/**").hasAuthority("USER")
//                    .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/dang-ky")
                .usernameParameter("email")
                .defaultSuccessUrl("/index", true)
                .permitAll()
            )
//            .rememberMe(rememberMe -> rememberMe.key("AbcdEfghIjkl..."))
            .logout(logout -> logout.logoutUrl("/signout").permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<NhanVien> user = nhanvienDao.findNhanVienByEmail(username);
            if (user.isPresent()) {
                return new User(user.get().getEmail(), user.get().getMatKhau(), user.get().getChucVu());
            }
            throw new UsernameNotFoundException(username);
        };
    }
}
