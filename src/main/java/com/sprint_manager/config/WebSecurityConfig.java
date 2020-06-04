package com.sprint_manager.config;

import com.sprint_manager.domain.UserRole;
import com.sprint_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers( "/registration").permitAll()
                    .antMatchers("/newsprint")
                        .hasAuthority(UserRole.MANAGER.toString())
                    .antMatchers("/sprint/{sprintId:\\d+}/delete",
                            "/sprint/{sprintId:\\d+}/add",
                            "/delete-from-sprint")
                            .hasAnyAuthority(UserRole.ANALYST.toString(), UserRole.MANAGER.toString())
                    .antMatchers(HttpMethod.POST,
                            "/sprint/{\\d+}/start",
                            "/sprint/{sprintId:\\d+}/finish",
                            "/sprint/{sprintId:\\d+}/edit-sprint")
                        .hasAuthority(UserRole.MANAGER.toString())
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}
