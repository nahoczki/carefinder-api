package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.Roles
import com.zolinahoczki.carefinderapi.entities.User
import com.zolinahoczki.carefinderapi.repositories.UserRepository
import com.zolinahoczki.carefinderapi.responseObjects.DetailedResponse
import com.zolinahoczki.carefinderapi.responseObjects.ErrorResponse
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class AuthController(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
) {

    @Value("\${carefinder.jwt.secret}")
    private val jwtPrivateKey: String? = null

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

                // Create JWT
                val issuer = user.email

                val jwt = Jwts.builder()
                    .setIssuer(issuer)
                    .setExpiration(Date(System.currentTimeMillis() + ((60 * 24 * 100) * 1000)))
                    .signWith(SignatureAlgorithm.HS512, jwtPrivateKey)
                    .compact()

                val headers = HttpHeaders()
                headers.add("Authorization", jwt)

                return ResponseEntity.ok()
                    .headers(headers)
                    .body(user)
            }
        }
        // By default, if user does not exist or password does not match, return this response
        return ResponseEntity.badRequest().body("User does not exist or password is incorrect")
    }

    fun changeUserRole(userEmailToEdit: String, role: String) : ResponseEntity<Any> {
        val user = userRepository.findOneByEmail(userEmailToEdit)

        if (user != null) {
            when (role.uppercase()) {
                "ADMIN" -> user.role = "ADMIN"
                "USER" -> user.role = "USER"
                else -> return ResponseEntity.badRequest().body(ErrorResponse("400 Bad Request", "Invalid role given"))
            }

            val savedUser = userRepository.save(user)
            return ResponseEntity.ok(DetailedResponse("Changed user's role to: $role", listOf(savedUser)))
        }

        return ResponseEntity.badRequest().body("User does not exist")
    }

    fun validateAdmin(jwt: String): Boolean {
        // Will try to see if user is an admin, if not get out >:(
        return try {
            val jwtBody = Jwts.parser().setSigningKey(jwtPrivateKey).parseClaimsJws(jwt).body
            val user = userRepository.findOneByEmail(jwtBody.issuer)
            if (user != null) {
                if (user.role == Roles.ADMIN.role) {
                    true
                } else {
                    throw Exception()
                }
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            false
        }
    }
}