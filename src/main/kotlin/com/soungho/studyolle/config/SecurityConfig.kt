package com.soungho.studyolle.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .mvcMatchers("/", "/login", "/sign-up", "/check-email", "/check-email-token",
                "/email-login", "/check-email-login", "login-link").permitAll()
            .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll()
            .anyRequest().authenticated()
    }
}