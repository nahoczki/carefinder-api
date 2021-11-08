package com.zolinahoczki.carefinderapi.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "users")
data class User(
    @Id
    val _id: ObjectId? = ObjectId(),
    val role: String = Roles.USER.role,
    val email: String,
    val password: String,
    val createdAt: Date? = Date(),
    val lastSignedIn: Date? = Date(),
)

enum class Roles(val role: String) {
    ADMIN("ADMIN"),
    USER("USER")
}
