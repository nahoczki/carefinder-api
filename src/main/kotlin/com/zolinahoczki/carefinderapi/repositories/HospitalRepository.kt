package com.zolinahoczki.carefinderapi.repositories

import com.zolinahoczki.carefinderapi.entities.Hospital
import org.springframework.data.mongodb.repository.MongoRepository

interface HospitalRepository : MongoRepository<Hospital, String> {
    fun findOneByName(name: String) : Hospital
}