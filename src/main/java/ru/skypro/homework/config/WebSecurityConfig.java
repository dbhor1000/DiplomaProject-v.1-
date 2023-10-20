package ru.skypro.homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import ru.skypro.homework.dto.Role;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class WebSecurityConfig {

    //Код для настройки аутентификации для Homework 4

    //@Autowired
    //private UserDetailsService userDetailsService;
    //@Bean
    //AuthenticationProvider authenticationProvider() {
    //    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //    provider.setUserDetailsService(userDetailsService);
    //    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    //    return provider;
    //}
    
    //@Override
    //protected void configure(HttpSecurity http) throws Exception {
    //    http.authorizeRequests().antMatchers("/login")
    //            .permitAll()
    //            .anyRequest()
    //            .authenticated()
    //            .and().httpBasic();
    //}

    // --- --- ---

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register",
            "/ads", "/ads/*/adPicture"
    };

    //@Bean
    //public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
    //    UserDetails user =
    //            User.builder()
    //                    .username("user@gmail.com")
    //                    .password("password")
    //                    .passwordEncoder(passwordEncoder::encode)
    //                    .roles(Role.USER.name())
    //                    .build();
    //    return new InMemoryUserDetailsManager(user);
    //}


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(
                        authorization ->
                                authorization
                                        .mvcMatchers(AUTH_WHITELIST)
                                        .permitAll()
                                        .mvcMatchers("/ads/**", "/users/**")
                                        .authenticated())
                .cors()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
