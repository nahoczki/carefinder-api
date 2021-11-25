package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.Hospital
import com.zolinahoczki.carefinderapi.repositories.HospitalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class HospitalController(
    @Autowired
    private val mongoTemplate: MongoTemplate,
) {
    private val params = listOf("providerId", "name", "city", "state", "zipCode", "county")

    /// TODO: Implement Methods
    // Get all hospitals controller
    fun getAll() : ResponseEntity<Any> {
        return ResponseEntity.ok(mongoTemplate.findAll(Hospital::class.java));
    }

    fun search(searchQuery: Map<String, String>) : ResponseEntity<Any> {

        val query = Query()

        searchQuery.forEach{
            if (!params.contains(it.key)) return ResponseEntity.badRequest().body("400 Bad Request")
            query.addCriteria(Criteria.where(it.key).regex(it.value.uppercase()))
        }

        return ResponseEntity.ok(mongoTemplate.find(query, Hospital::class.java))
    }
}