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
    val rating: Number? = null,
    val ratingNote: String? = null,
    val image: String? = null,
    val googleRating: GoogleRating? = null,
    val needsReview: Boolean,
)

data class HospitalLocation(
    val latitude: Double,
    val longitude: Double,
)

data class GoogleRating(
    val rating: Number? = 0,
    val reviews: Array<GoogleReview>? = emptyArray()
)

data class GoogleReview(
    val author_name: String? = "",
    val author_url: String? = "",
    val language: String? = "",
    val profile_photo_url: String? = "",
    val rating: Number? = 0,
    val relative_time_description: String? = "",
    val text: String? = "",
    val time: Number? = 0
)
