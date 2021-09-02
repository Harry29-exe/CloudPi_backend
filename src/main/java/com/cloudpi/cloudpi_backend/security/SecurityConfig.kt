package com.cloudpi.cloudpi_backend.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
class SecurityConfig(passwordEncoder: PasswordEncoder, userService: UserDetailsService) :
    WebSecurityConfigurerAdapter() {

    private val authenticationProvider: DaoAuthenticationProvider = DaoAuthenticationProvider()


    init {
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        authenticationProvider.setUserDetailsService(userService)
    }

    override fun configure(http: HttpSecurity?) {
        http as HttpSecurity
        http
            .csrf().disable()
            .cors().disable()
            .authorizeRequests().anyRequest().permitAll()
    }

    @Bean
    fun getAuthenticationProvider(): AuthenticationProvider {
        return authenticationProvider
    }

}