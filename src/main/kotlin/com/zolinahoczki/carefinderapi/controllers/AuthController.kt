package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.User
import com.zolinahoczki.carefinderapi.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import java.util.*
import javax.servlet.http.HttpServletResponse

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

                // Create JWT
                val issuer = user.email

                val jwt = Jwts.builder()
                    .setIssuer(issuer)
                    .setExpiration(Date(System.currentTimeMillis() + ((60 * 24 * 100) * 1000)))
                    .signWith(SignatureAlgorithm.HS512, "\${carefinder.jwt.secret}")
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
}