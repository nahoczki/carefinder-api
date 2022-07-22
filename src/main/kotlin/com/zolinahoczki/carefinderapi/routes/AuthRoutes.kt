package com.zolinahoczki.carefinderapi.routes

import com.zolinahoczki.carefinderapi.controllers.AuthController
import com.zolinahoczki.carefinderapi.responseObjects.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthRoutes(
    private val authController: AuthController,
) {

    @GetMapping("/users")
    fun getAllUsers(@RequestHeader(required = false) Authorization: String?): ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                authController.getAll()
            } else {
                ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
            }
        } else {
            ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
        }
    }

    @PutMapping("/updaterole")
    fun updateUsersRole(
        @RequestHeader(required = false) Authorization: String?,
        @RequestParam(required = true, value = "email") toUpdateEmail: String,
        @RequestParam(required = true, value = "role") roleToUpdateTo: String
    ) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                authController.changeUserRole(toUpdateEmail, roleToUpdateTo)
            } else {
                ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
            }
        } else {
            ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
        }
    }

    @PostMapping("/register")
    fun registerUser(
        @RequestParam(required = true) email: String,
        @RequestParam(required = true) password: String
    ): ResponseEntity<Any> {
        return authController.register(email, password)
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestParam(required = true) email: String,
        @RequestParam(required = true) password: String
    ): ResponseEntity<Any> {
        return authController.login(email, password)
    }
}