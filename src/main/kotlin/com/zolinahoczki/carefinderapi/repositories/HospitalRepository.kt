package com.zolinahoczki.carefinderapi.repositories

import com.zolinahoczki.carefinderapi.entities.Hospital
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.core.query.Query

interface HospitalRepository : MongoRepository<Hospital, String>{
    fun findAllByNameRegex(name: String) : List<Hospital>

//    @Query(searchParams)
//    fun findBySearch(searchParams: Map<String, String>) : List<Hospital>? {
//        return null
//    }
}