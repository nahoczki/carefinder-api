package com.zolinahoczki.carefinderapi.controllers

import com.zolinahoczki.carefinderapi.entities.Hospital
import com.zolinahoczki.carefinderapi.repositories.HospitalRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hospitals")
class HospitalController(
    private val hospitalRepository: HospitalRepository
) {
    /// TODO: Implement Methods

    @GetMapping
    fun getAllHospitals() : ResponseEntity<List<Hospital>> {
        val hospitals = hospitalRepository.findAll()
        return ResponseEntity.ok(hospitals)
    }
}