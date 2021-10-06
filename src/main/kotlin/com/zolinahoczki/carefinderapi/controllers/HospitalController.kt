package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.Hospital
import com.zolinahoczki.carefinderapi.repositories.HospitalRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Controller

@Controller
class HospitalController(
    private val hospitalRepository: HospitalRepository,
    private val mongoTemplate: MongoTemplate
) {
    /// TODO: Implement Methods
    fun getAll() : List<Hospital> {
        return hospitalRepository.findAll()
    }

    fun search(searchQuery: Map<String, String>) : List<Hospital>? {

        val query = Query()

        searchQuery.forEach{
            query.addCriteria(Criteria.where(it.key).isEqualTo(it.value))
        }

        return mongoTemplate.find(query, Hospital::class.java)
    }
}