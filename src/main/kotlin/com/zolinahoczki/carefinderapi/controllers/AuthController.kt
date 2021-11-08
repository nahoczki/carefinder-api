package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.User
import com.zolinahoczki.carefinderapi.repositories.UserRepository
import org.springframework.stereotype.Controller

@Controller
class AuthController(
    private val userRepository: UserRepository
) {

    fun getAll() : List<User> {
        return userRepository.findAll()
    }
}