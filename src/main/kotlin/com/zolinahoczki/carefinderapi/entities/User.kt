package com.zolinahoczki.carefinderapi.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "users")
data class User(
    @Id
    val _id: ObjectId? = ObjectId(),
    var role: String? = Roles.USER.role,
    val email: String,
    @JsonIgnore
    val password: String,
    val createdAt: Date? = Date(),
    var lastSignedIn: Date? = Date(),
)

enum class Roles(val role: String) {
    ADMIN("ADMIN"),
    USER("USER")
}
