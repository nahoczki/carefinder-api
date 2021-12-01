package com.zolinahoczki.carefinderapi.repositories

import com.zolinahoczki.carefinderapi.entities.ApiKey
import org.springframework.data.mongodb.repository.MongoRepository

interface ApiKeyRepository : MongoRepository<ApiKey, String>{
    fun findOneByName(name: String) : ApiKey?
    fun findOneByApiKey(apikey: String) : ApiKey?
    fun existsByApiKey(apiKey: String) : Boolean
    fun existsByName(name: String) : Boolean
    fun deleteApiKeyByApiKey(apikey: String) : ApiKey
    fun deleteApiKeyByName(name: String) : ApiKey

//    @Query(searchParams)
//    fun findBySearch(searchParams: Map<String, String>) : List<Hospital>? {
//        return null
//    }
}