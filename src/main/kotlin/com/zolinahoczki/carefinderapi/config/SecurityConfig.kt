package com.zolinahoczki.carefinderapi.config

import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsUtils
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter()   {

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder(5)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
//        http.csrf().disable();
        http.cors().and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll().anyRequest().authenticated()
    }
}