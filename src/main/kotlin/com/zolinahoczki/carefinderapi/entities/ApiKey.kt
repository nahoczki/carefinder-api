package com.zolinahoczki.carefinderapi.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*

@Document(collection = "apikeys")
data class ApiKey(
    @Id
    val _id: ObjectId? = ObjectId(),
    val name: String,
    val apiKey: String,
    val createdAt: Date? = Date(),
)