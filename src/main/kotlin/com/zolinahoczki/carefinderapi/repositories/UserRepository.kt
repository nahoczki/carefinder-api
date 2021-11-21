package com.zolinahoczki.carefinderapi.repositories

import com.zolinahoczki.carefinderapi.entities.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun existsByEmail(email: String) : Boolean
    fun findOneByEmail(email: String) : User?
}