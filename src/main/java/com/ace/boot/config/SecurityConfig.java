package com.ace.boot.config;

import com.ace.boot.entity.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;

@Configuration

@EnableWebSecurity

public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin(login->login.loginPage("/login").usernameParameter("name").loginProcessingUrl("/login").defaultSuccessUrl("/"))
                .logout(logout->logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
                .authorizeHttpRequests(http->{
//                    http.requestMatchers( "forgotPassword","/admin/userRegister","/","/login","/member/userRegister","/member/changePassword","/changePassword").permitAll();
//                    http.requestMatchers("/admin/**").hasAnyAuthority(Role.ADMIN.name());
//                    http.requestMatchers("/member/**").hasAnyAuthority(Role.MEMBER.name(),Role.ADMIN.name());
//                    http.anyRequest().authenticated();
                    http.anyRequest().permitAll();
                })
                .exceptionHandling().accessDeniedPage("/error");
                return httpSecurity.build();
    }
}