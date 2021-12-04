package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.ApiKey
import com.zolinahoczki.carefinderapi.entities.Hospital
import com.zolinahoczki.carefinderapi.repositories.ApiKeyRepository
import com.zolinahoczki.carefinderapi.responseObjects.DetailedResponse
import com.zolinahoczki.carefinderapi.responseObjects.ErrorResponse
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
    fun getAll(Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                ResponseEntity.ok(apiKeyRepository.findAll())
            } else {
                ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
            }
        } else {
            ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
        }
    }

    fun searchByName(name: String, Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                ResponseEntity.ok(apiKeyRepository.findOneByName(name))
            } else {
                ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
            }
        } else {
            ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
        }
    }

    fun searchByApiKey(apiKey: String, Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                ResponseEntity.ok(apiKeyRepository.findOneByApiKey(apiKey))
            } else {
                ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
            }
        } else {
            ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
        }
    }

    fun deleteAll(Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                apiKeyRepository.deleteAll()
                ResponseEntity.ok("Successfully deleted all keys")
            } else {
                ResponseEntity.status(401).body("Unauthorized")
            }
        } else {
            ResponseEntity.status(401).body("Unauthorized")
        }
    }

    fun deleteByApiKey(apiKey: String, Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                if (!apiKeyRepository.existsByApiKey(apiKey)) {
                    ResponseEntity.badRequest().body(ErrorResponse("400 Bad request", "There is no Api key with the key: $apiKey"))
                }
                ResponseEntity.ok(DetailedResponse("Successfully removed api key", listOf(apiKeyRepository.deleteApiKeyByApiKey(apiKey))))
            } else {
                ResponseEntity.status(401).body("Unauthorized")
            }
        } else {
            ResponseEntity.status(401).body("Unauthorized")
        }
    }

    fun deleteByName(name: String, Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                if (!apiKeyRepository.existsByName(name)) {
                    ResponseEntity.badRequest().body(ErrorResponse("400 Bad request", "There is no Api key with the name: $name"))
                }
                ResponseEntity.ok(DetailedResponse("Successfully removed api key", listOf(apiKeyRepository.deleteApiKeyByName(name))))
            } else {
                ResponseEntity.status(401).body("Unauthorized")
            }
        } else {
            ResponseEntity.status(401).body("Unauthorized")
        }
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