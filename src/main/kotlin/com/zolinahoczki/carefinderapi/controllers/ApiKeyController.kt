package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.ApiKey
import com.zolinahoczki.carefinderapi.repositories.ApiKeyRepository
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class ApiKeyController(
    private val apiKeyRepository: ApiKeyRepository,
    private val mongoTemplate: MongoTemplate,
    ) {

    private val params = listOf("apikey", "name")

    /// TODO: Implement Methods
    fun getAll() : List<ApiKey> {
        return apiKeyRepository.findAll()
    }

    fun search(searchQuery: Map<String, String>) : List<ApiKey>? {

        val query = Query()

        searchQuery.forEach{
            if (!params.contains(it.key)) return null
            query.addCriteria(Criteria.where(it.key).regex(it.value.uppercase()))
        }

        return mongoTemplate.find(query, ApiKey::class.java)
    }

    fun create(name: String) : ApiKey? {
        if (!apiKeyRepository.existsByName(name)) {
            val createdDate = Date()
            var genKey = UUID.randomUUID().toString()

            // If generated key exists, keep generating until we find a unique one
            while(apiKeyRepository.existsByApiKey(genKey)) {
                genKey = UUID.randomUUID().toString()
            }

            return mongoTemplate.insert(ApiKey(ObjectId(), name, genKey, createdDate))
        } else {
            return null
        }
    }
}