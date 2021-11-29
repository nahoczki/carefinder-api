package com.zolinahoczki.carefinderapi.routes

import com.zolinahoczki.carefinderapi.controllers.HospitalController
import com.zolinahoczki.carefinderapi.entities.Hospital
import com.zolinahoczki.carefinderapi.requestObjects.HospitalCreateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/hospitals")
class HospitalRoutes(
    private val hospitalController: HospitalController
) {

    @GetMapping("")
    fun getHospitals(@RequestParam(required = false) params: Map<String, String>?) : ResponseEntity<Any> {
        // Params are optional but are used to search
        return if (params == null || params.isEmpty()) {
            hospitalController.getAll()
        } else {
            hospitalController.search(params)
        }
    }

    @PostMapping("")
    fun createHospital(@RequestBody body: HospitalCreateRequest,
                       @RequestHeader(required = false) Authorization: String?) : ResponseEntity<Any> {
        return hospitalController.createHospital(body, Authorization)
    }

    @DeleteMapping("")
    fun deleteHospital(@RequestParam(required = false) params: Map<String, String>?,
                       @RequestHeader(required = false) Authorization: String?) : ResponseEntity<Any> {
        return if (params == null || params.isEmpty()) {
            hospitalController.removeAll(Authorization)
        } else {
            hospitalController.removeBySearch(params, Authorization)
        }
    }
}