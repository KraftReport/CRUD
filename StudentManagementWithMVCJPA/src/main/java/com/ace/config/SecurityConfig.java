package com.ace.config;

import com.ace.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(formLogin->formLogin.loginPage("/login")
                                                    .usernameParameter("email")
                                                    .loginProcessingUrl("/login")
                                                    .defaultSuccessUrl("/welcome"));

        httpSecurity.logout(logOut->logOut.logoutSuccessUrl("/logout")
                                            .logoutSuccessUrl("/"));

        httpSecurity.authorizeHttpRequests(http->{
            http.requestMatchers("/userManager","/userUpdate","/userDelete","/courseManager","/courseUpdate","/courseRegister","/courseDelete").hasRole(Role.ADMIN.name());
            http.anyRequest().permitAll();
        });
        return httpSecurity.build();
    }
}
