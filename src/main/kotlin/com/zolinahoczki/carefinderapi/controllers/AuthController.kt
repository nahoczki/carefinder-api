package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.User
import com.zolinahoczki.carefinderapi.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller

@Controller
class AuthController(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
) {

    fun getAll() : List<User> {
        return userRepository.findAll()
    }

    fun register(email: String, password: String) : User? {

        if (userRepository.existsByEmail(email)) {
            return null
        }

        return userRepository.save(User(email = email, password = passwordEncoder.encode(password)))
    }

    fun login(email: String, password: String) : User? {

        val user = userRepository.findOneByEmail(email)

        if (user != null) {
            if (passwordEncoder.matches(password, user.password)) {
                return user
            }
            return null
        } else {
            return null
        }
    }
}