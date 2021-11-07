package com.zolinahoczki.carefinderapi.routes

import com.zolinahoczki.carefinderapi.controllers.ApiKeyController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/apikeys")
class ApiKeyRoutes(
    private val apiKeyController: ApiKeyController
) {
    @GetMapping("")
    fun getApiKeys(@RequestParam(required = false) params: Map<String, String>?) : ResponseEntity<Any> {
        // Params are optional but are used to search
        return if (params == null) {
            ResponseEntity.ok(apiKeyController.getAll())
        } else {
            val data = apiKeyController.search(params)

            if (data != null) {
                ResponseEntity.ok(data)
            } else {
                ResponseEntity.badRequest().body("400 Bad Request")
            }
        }
    }

    @PostMapping("")
    fun create(@RequestParam(required = true) name: String) : ResponseEntity<Any> {

        val created = apiKeyController.create(name)

        return if (created != null) {
            ResponseEntity.ok(created)
        } else {
            ResponseEntity.badRequest().body("Error Creating ApiKey: Name taken")
        }
    }
}