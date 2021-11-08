package com.zolinahoczki.carefinderapi.routes

import com.zolinahoczki.carefinderapi.controllers.AuthController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/auth")
class AuthRoutes(
    private val authController: AuthController
) {

    @GetMapping("/users")
    fun getAllUsers() : ResponseEntity<Any> {
        // Params are optional but are used to search
        return ResponseEntity.ok(authController.getAll())
    }

    @PostMapping("/register")
    fun registerUser() : ResponseEntity<Any> {
        return ResponseEntity.ok(authController.getAll())
    }
}