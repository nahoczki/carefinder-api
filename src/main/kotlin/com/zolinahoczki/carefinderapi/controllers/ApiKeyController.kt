package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.ApiKey
import com.zolinahoczki.carefinderapi.repositories.ApiKeyRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class ApiKeyController(
    private val authController: AuthController,
    @Autowired
    private val apiKeyRepository: ApiKeyRepository,
    @Autowired
    private val mongoTemplate: MongoTemplate,
    ) {

    /// TODO: Implement Methods
    fun getAll() : ResponseEntity<Any> {
        return ResponseEntity.ok(apiKeyRepository.findAll())
    }

    fun searchByName(name: String) : ResponseEntity<Any> {
        return ResponseEntity.ok(apiKeyRepository.findOneByName(name))
    }

    fun searchByApiKey(apiKey: String) : ResponseEntity<Any> {
        return ResponseEntity.ok(apiKeyRepository.findOneByApiKey(apiKey))
    }

    fun create(name: String) : ResponseEntity<Any> {
        return if (!apiKeyRepository.existsByName(name)) {
            var genKey = UUID.randomUUID().toString()

            // If generated key exists, keep generating until we find a unique one
            while(apiKeyRepository.existsByApiKey(genKey)) {
                genKey = UUID.randomUUID().toString()
            }

            ResponseEntity.ok(mongoTemplate.insert(ApiKey(name = name, apiKey = genKey)))
        } else {
            ResponseEntity.badRequest().body("Error: Api key name already exists")
        }
    }
}