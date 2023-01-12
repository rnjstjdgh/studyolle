package com.soungho.studyolle.config

import com.soungho.studyolle.account.AccountService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import javax.sql.CommonDataSource
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val accountService: AccountService,
    private val dataSource: DataSource
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .mvcMatchers("/", "/login", "/sign-up", "/check-email-token",
                "/email-login", "/check-email-login", "login-link").permitAll()
            .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll()
            .anyRequest().authenticated()

        http.formLogin()
            .loginPage("/login").permitAll()

        http.logout()
            .logoutSuccessUrl("/")

        http.rememberMe()
            .tokenRepository(tokenRepository())
    }

    @Bean
    fun tokenRepository(): PersistentTokenRepository {
        val jdbcTokenRepository = JdbcTokenRepositoryImpl()
        jdbcTokenRepository.setDataSource(dataSource)
        return jdbcTokenRepository
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .mvcMatchers("/node_modules/**")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }
}