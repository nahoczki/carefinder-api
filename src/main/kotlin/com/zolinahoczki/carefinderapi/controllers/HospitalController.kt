package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.Hospital
import com.zolinahoczki.carefinderapi.entities.HospitalLocation
import com.zolinahoczki.carefinderapi.requestObjects.HospitalCreateRequest
import com.zolinahoczki.carefinderapi.responseObjects.DetailedResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.where
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class HospitalController(
    @Autowired
    private val authController: AuthController,
    @Autowired
    private val mongoTemplate: MongoTemplate,
) {
    private val params = listOf("providerId", "name", "city", "state", "zipCode", "county")

    // Getters
    fun getAll() : ResponseEntity<Any> {
        return ResponseEntity.ok(mongoTemplate.findAll(Hospital::class.java));
    }

    fun search(searchQuery: Map<String, String>) : ResponseEntity<Any> {

        val query = Query()

        searchQuery.forEach{
            if (!params.contains(it.key)) return ResponseEntity.badRequest().body("400 Bad Request")
            query.addCriteria(Criteria.where(it.key).regex(it.value.uppercase()))
        }

        return ResponseEntity.ok(mongoTemplate.find(query, Hospital::class.java))
    }

    // Deletes
    fun removeAll(Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                val removed = mongoTemplate.findAllAndRemove(Query(), Hospital::class.java)
                ResponseEntity.ok(DetailedResponse("Successfully Removed Hospital(s)", removed))
            } else {
                ResponseEntity.status(401).body("Unauthorized")
            }
        } else {
            ResponseEntity.status(401).body("Unauthorized")
        }
    }

    fun removeBySearch(searchQuery: Map<String, String>, Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                val query = Query()

                searchQuery.forEach{
                    if (!params.contains(it.key)) return ResponseEntity.badRequest().body("400 Bad Request")
                    query.addCriteria(Criteria.where(it.key).regex(it.value.uppercase()))
                }

                val removed = mongoTemplate.findAllAndRemove(query, Hospital::class.java).toList()

                return ResponseEntity.ok(DetailedResponse("Successfully Removed Hospital(s)", removed))
            } else {
                ResponseEntity.status(401).body("Unauthorized")
            }
        } else {
            ResponseEntity.status(401).body("Unauthorized")
        }
    }

    // Update/Create

    fun createHospital(hospitalToCreate: HospitalCreateRequest, Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {

                if (mongoTemplate.exists(Query().addCriteria(Criteria.where("providerId").isEqualTo(hospitalToCreate.providerId)), Hospital::class.java)) {
                    return ResponseEntity.badRequest().body("Hospital with providerId: ${hospitalToCreate.providerId} already exists")
                }

                val hospital = Hospital(
                    providerId = hospitalToCreate.providerId,
                    location = HospitalLocation(
                        humanAddress = "",
                        latitude = hospitalToCreate.lat,
                        longitude = hospitalToCreate.long,
                        needsRecoding = "false"
                    ),
                    name = hospitalToCreate.name,
                    address = hospitalToCreate.address,
                    city = hospitalToCreate.city,
                    state = hospitalToCreate.state,
                    zipCode = hospitalToCreate.zipCode,
                    county = hospitalToCreate.county,
                    phoneNumber = hospitalToCreate.phoneNumber,
                    hospitalType = hospitalToCreate.hospitalType,
                    ownership = hospitalToCreate.ownership,
                    emergencyServices = hospitalToCreate.emergencyServices
                )

                val added = mongoTemplate.insert(hospital)

                return ResponseEntity.ok(DetailedResponse("Successfully Saved Hospital", listOf(added)))
            } else {
                ResponseEntity.status(401).body("Unauthorized")
            }
        } else {
            ResponseEntity.status(401).body("Unauthorized")
        }
    }
}