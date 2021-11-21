package com.zolinahoczki.carefinderapi.routes

import com.zolinahoczki.carefinderapi.controllers.AuthController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/auth")
class AuthRoutes(
    private val authController: AuthController,
) {

    @GetMapping("/users")
    fun getAllUsers() : ResponseEntity<Any> {
        // Params are optional but are used to search
        return ResponseEntity.ok(authController.getAll())
    }

    @PostMapping("/register")
    fun registerUser(@RequestParam(required = true) email: String, @RequestParam(required = true) password: String) : ResponseEntity<Any> {
        val created = authController.register(email, password)

        return if (created != null) {
            ResponseEntity.ok(created)
        } else {
            ResponseEntity.badRequest().body("Error Creating ApiKey: Name taken")
        }
    }

    @PostMapping("/login")
    fun loginUser(@RequestParam(required = true) email: String, @RequestParam(required = true) password: String) : ResponseEntity<Any> {
        val user = authController.login(email, password)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.badRequest().body("Wrong password")
        }
    }
}