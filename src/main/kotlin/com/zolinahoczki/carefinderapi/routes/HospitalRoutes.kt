package com.zolinahoczki.carefinderapi.routes

import com.zolinahoczki.carefinderapi.controllers.HospitalController
import com.zolinahoczki.carefinderapi.entities.Hospital
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/hospitals")
class HospitalRoutes(
    private val hospitalController: HospitalController
) {

    @GetMapping("")
    fun getHospitals(@RequestParam(required = false) params: Map<String, String>?) : ResponseEntity<List<Hospital>> {
        // Params are optional but are used to search
        return if (params == null) {
            ResponseEntity.ok(hospitalController.getAll())
        } else {
            val data = hospitalController.search(params)

            if (data != null) {
                ResponseEntity.ok(data)
            } else {
                ResponseEntity.badRequest().build()
            }
        }
    }
}