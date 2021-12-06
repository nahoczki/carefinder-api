package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.Hospital
import com.zolinahoczki.carefinderapi.entities.HospitalLocation
import com.zolinahoczki.carefinderapi.requestObjects.HospitalCreateRequest
import com.zolinahoczki.carefinderapi.responseObjects.DetailedResponse
import com.zolinahoczki.carefinderapi.responseObjects.ErrorResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class HospitalController(
    private val authController: AuthController,
    @Autowired
    private val mongoTemplate: MongoTemplate,
) {
    private val params = listOf("providerId", "name", "city", "state", "zipCode", "county")
    private val allowedEdits = listOf("providerId", "name", "city", "state", "zipCode", "county", "phoneNumber")

    // Getters
    fun getAll() : ResponseEntity<Any> {
        return ResponseEntity.ok(DetailedResponse("Successfully retrieved hospital data", mongoTemplate.findAll(Hospital::class.java)));
    }

    fun search(searchQuery: Map<String, String>) : ResponseEntity<Any> {

        val query = Query()

        searchQuery.forEach{
            if (!params.contains(it.key)) return ResponseEntity.badRequest().body(ErrorResponse("400 Bad Request", "${it.key} is not a valid search parameter"))
            query.addCriteria(Criteria.where(it.key).regex(it.value.uppercase()))
        }

        return ResponseEntity.ok(DetailedResponse("Successfully retrieved hospital data", mongoTemplate.find(query, Hospital::class.java)))
    }

    // Deletes
    fun removeAll(Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                val removed = mongoTemplate.findAllAndRemove(Query(), Hospital::class.java)
                ResponseEntity.ok(DetailedResponse("Successfully Removed Hospital(s)", removed))
            } else {
                ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
            }
        } else {
            ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
        }
    }

    fun removeBySearch(searchQuery: Map<String, String>, Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {
                val query = Query()

                searchQuery.forEach{
                    if (!params.contains(it.key)) return ResponseEntity.badRequest().body(ErrorResponse("400 Bad Request", "${it.key} is not a valid search parameter"))
                    query.addCriteria(Criteria.where(it.key).regex(it.value.uppercase()))
                }

                val removed = mongoTemplate.findAllAndRemove(query, Hospital::class.java).toList()

                return ResponseEntity.ok(DetailedResponse("Successfully Removed Hospital(s)", removed))
            } else {
                ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
            }
        } else {
            ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
        }
    }

    // Update/Create

    fun createHospital(hospitalToCreate: HospitalCreateRequest, Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {

                if (mongoTemplate.exists(Query().addCriteria(Criteria.where("providerId").isEqualTo(hospitalToCreate.providerId)), Hospital::class.java)) {
                    return ResponseEntity.status(409).body(ErrorResponse("409 Conflicting Data", "Hospital with providerId: ${hospitalToCreate.providerId} already exists"))
                }

                val hospital = Hospital(
                    providerId = hospitalToCreate.providerId,
                    location = HospitalLocation(
                        humanAddress = "{\"address\":\"${hospitalToCreate.address}\",\"city\":\"${hospitalToCreate.city}\",\"state\":\"${hospitalToCreate.state.uppercase()}\",\"zip\":\"${hospitalToCreate.zipCode}\"}",
                        latitude = hospitalToCreate.lat,
                        longitude = hospitalToCreate.long,
                        needsRecoding = "false"
                    ),
                    name = hospitalToCreate.name,
                    address = hospitalToCreate.address,
                    city = hospitalToCreate.city,
                    state = hospitalToCreate.state.uppercase(),
                    zipCode = hospitalToCreate.zipCode,
                    county = hospitalToCreate.county,
                    phoneNumber = hospitalToCreate.phoneNumber,
                    hospitalType = hospitalToCreate.hospitalType,
                    ownership = hospitalToCreate.ownership,
                    emergencyServices = hospitalToCreate.emergencyServices
                )

                val added = mongoTemplate.insert(hospital)
                return ResponseEntity.status(201).body(DetailedResponse("Successfully Saved Hospital", added))
            } else {
                ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
            }
        } else {
            ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
        }
    }

    fun updateHospital(providerId: String, stuffToUpdate: Map<String, String>, Authorization: String?) : ResponseEntity<Any> {
        return if (Authorization != null) {
            if (authController.validateAdmin(Authorization)) {

                if (!mongoTemplate.exists(Query().addCriteria(Criteria.where("providerId").isEqualTo(providerId)), Hospital::class.java)) {
                    return ResponseEntity.badRequest().body(ErrorResponse("400 Bad Request","Hospital with providerId $providerId does not exist"))
                }

                val query = Query(Criteria.where("providerId").isEqualTo(providerId))
                val update = Update()

                stuffToUpdate.forEach{
                    if (!allowedEdits.contains(it.key)) return ResponseEntity.badRequest().body(ErrorResponse("400 Bad Request", "${it.key} is not a valid key to edit"))
                    update.set(it.key, it.value)
                }

                mongoTemplate.updateFirst(query, update, Hospital::class.java)

                val updated = mongoTemplate.findOne(query, Hospital::class.java)

                return ResponseEntity.ok(DetailedResponse("Successfully Updated Hospital", updated!!))
            } else {
                ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
            }
        } else {
            ResponseEntity.status(401).body(ErrorResponse("401 Unauthorized", "Access Denied."))
        }
    }
}