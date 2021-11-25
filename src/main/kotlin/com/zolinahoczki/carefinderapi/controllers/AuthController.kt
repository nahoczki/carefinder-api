package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.User
import com.zolinahoczki.carefinderapi.repositories.UserRepository
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller

@Controller
class AuthController(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
) {

    fun getAll() : ResponseEntity<Any> {
        return ResponseEntity.ok(userRepository.findAll())
    }

    fun register(email: String, password: String) : ResponseEntity<Any> {

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Error signing up; Email already in use")
        }

        return ResponseEntity.ok(userRepository.save(User(email = email, password = passwordEncoder.encode(password))))
    }

    fun login(email: String, password: String) : ResponseEntity<Any> {

        val user = userRepository.findOneByEmail(email)

        if (user != null) {
            if (passwordEncoder.matches(password, user.password)) {
                return ResponseEntity.ok(user)
            }
        }
        // By default, if user does not exist or password does not match, return this response
        return ResponseEntity.badRequest().body("User does not exist or password is incorrect")
    }
}