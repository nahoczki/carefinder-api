package com.zolinahoczki.carefinderapi.routes

import com.zolinahoczki.carefinderapi.controllers.HospitalController
import com.zolinahoczki.carefinderapi.entities.Hospital
import com.zolinahoczki.carefinderapi.requestObjects.HospitalCreateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/hospitals")
class HospitalRoutes(
    private val hospitalController: HospitalController
) {

    @GetMapping("")
    fun getHospitals(@RequestParam(required = false) params: Map<String, String>?,
                     @RequestHeader(required = false) format: String = "json") : ResponseEntity<Any> {
        // Params are optional but are used to search
        return if (params == null || params.isEmpty()) {
            hospitalController.getAll(format.lowercase(Locale.getDefault()))
        } else {
            hospitalController.search(params, format.lowercase(Locale.getDefault()))
        }
    }

//    @PostMapping("")
//    fun createHospital(@RequestBody body: HospitalCreateRequest,
//                       @RequestHeader(required = false) Authorization: String?) : ResponseEntity<Any> {
//        return hospitalController.createHospital(body, Authorization)
//    }

    @DeleteMapping("")
    fun deleteHospital(@RequestParam(required = false) params: Map<String, String>?,
                       @RequestHeader(required = false) Authorization: String?) : ResponseEntity<Any> {
        return if (params == null || params.isEmpty()) {
            hospitalController.removeAll(Authorization)
        } else {
            hospitalController.removeBySearch(params, Authorization)
        }
    }

    @PutMapping("")
    fun updateHospital(@RequestParam(required = true) providerId: String,
                       @RequestBody(required = true) body: Map<String, String>,
                       @RequestHeader(required = false) Authorization: String?) : ResponseEntity<Any> {
        return hospitalController.updateHospital(providerId, body, Authorization)
    }
}