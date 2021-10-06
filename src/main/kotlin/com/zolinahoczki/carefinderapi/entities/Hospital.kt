package com.zolinahoczki.carefinderapi.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospitals")
data class Hospital(
    @Id
    val id: ObjectId = ObjectId.get(),
    val location: HospitalLocation,
    val providerId: String,
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val county: String,
    val phoneNumber: String,
    val hospitalType: String,
    val ownership: String,
    val emergencyServices: String,
)

data class HospitalLocation(
    val humanAddress: String,
    val latitude: Long,
    val longitude: Long,
    val needsRecoding: String
)