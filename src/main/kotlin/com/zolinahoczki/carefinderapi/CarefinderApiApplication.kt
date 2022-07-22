package com.zolinahoczki.carefinderapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class CarefinderApiApplication

fun main(args: Array<String>) {
    runApplication<CarefinderApiApplication>(*args)

    @Bean
    fun configure(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/*").allowedOrigins("*")
            }
        }
    }
}
