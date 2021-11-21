package com.zolinahoczki.carefinderapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class CarefinderApiApplication

fun main(args: Array<String>) {
    runApplication<CarefinderApiApplication>(*args)
}
