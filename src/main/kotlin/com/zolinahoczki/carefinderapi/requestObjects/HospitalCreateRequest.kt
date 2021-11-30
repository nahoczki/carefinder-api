package com.zolinahoczki.carefinderapi.requestObjects

import lombok.Value

@Value
class HospitalCreateRequest (
    val providerId: String,
    val lat: Double,
    val long: Double,
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