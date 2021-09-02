package com.cloudpi.cloudpi_backend.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordEncoderBean {

    @Bean
    fun createPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10)
    }
}