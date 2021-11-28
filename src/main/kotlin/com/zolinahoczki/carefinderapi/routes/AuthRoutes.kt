package com.zolinahoczki.carefinderapi.routes

import com.zolinahoczki.carefinderapi.controllers.AuthController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/auth")
class AuthRoutes(
    private val authController: AuthController,
) {

    @GetMapping("/users")
    fun getAllUsers() : ResponseEntity<Any> {
        return authController.getAll()
    }

    @PostMapping("/register")
    fun registerUser(@RequestParam(required = true) email: String, @RequestParam(required = true) password: String) : ResponseEntity<Any> {
        return authController.register(email, password)
    }

    @PostMapping("/login")
    fun loginUser(@RequestParam(required = true) email: String,
                  @RequestParam(required = true) password: String) : ResponseEntity<Any> {
        return authController.login(email, password)
    }
}