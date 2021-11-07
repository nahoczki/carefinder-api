package com.zolinahoczki.carefinderapi.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospitals")
data class Hospital(
    @Id
    val _id: ObjectId? = ObjectId(),
    val providerId: String,
    val location: HospitalLocation,
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val county: String? = "",
    val phoneNumber: String,
    val hospitalType: String,
    val ownership: String,
    val emergencyServices: String,
)

data class HospitalLocation(
    val humanAddress: String,
    val latitude: Double,
    val longitude: Double,
    val needsRecoding: String
)
