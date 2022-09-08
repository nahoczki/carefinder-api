package com.zolinahoczki.carefinderapi.helpers

import com.zolinahoczki.carefinderapi.entities.Hospital

class GeoJSONCreator {

    fun fromHospitals(hospitals: MutableList<Hospital>) : Map<String, Any> {
        val featureCollection: MutableMap<String, Any> = emptyMap<String, Any>().toMutableMap()
        val features: MutableList<Map<String, Any>> = emptyList<Map<String, Any>>().toMutableList()

        hospitals.forEach { hospital ->
            val feature = emptyMap<String, Any>().toMutableMap()
            val properties = emptyMap<String, String>().toMutableMap()
            val geometry = emptyMap<String, Any>().toMutableMap()

            // Set up properties
            properties["providerId"] = hospital.providerId
            properties["name"] = hospital.name
            properties["emergencyServices"] = hospital.emergencyServices
            properties["address"] = hospital.address
            properties["rating"] = hospital.rating.toString()
            properties["ratingNote"] = hospital.ratingNote
            properties["image"] = hospital.image

            // Set up geometry
            geometry["type"] = "Point"
            geometry["coordinates"] = arrayOf(hospital.location.longitude, hospital.location.latitude)

            // Set up feature
            feature["type"] = "Feature"
            feature["properties"] = properties
            feature["geometry"] = geometry

            features.add(feature)
        }

        featureCollection["type"] = "FeatureCollection"
        featureCollection["features"] = features

        return featureCollection

    }
}