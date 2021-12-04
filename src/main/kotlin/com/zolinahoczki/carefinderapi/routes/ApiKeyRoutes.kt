package com.zolinahoczki.carefinderapi.routes

import com.zolinahoczki.carefinderapi.controllers.ApiKeyController
import com.zolinahoczki.carefinderapi.responseObjects.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/apikeys")
class ApiKeyRoutes(
    private val apiKeyController: ApiKeyController
) {
    @GetMapping("")
    fun getApiKeys(@RequestParam(required = false) params: Map<String, String>?,
                   @RequestHeader(required = false) Authorization: String?) : ResponseEntity<Any> {
        // Params are optional but are used to search
        return if (params == null || params.isEmpty()) {
            apiKeyController.getAll(Authorization)
        } else {
            if (params.contains("name")) {
                apiKeyController.searchByName(params.getValue("name"), Authorization)
            } else if (params.contains("apikey")) {
                apiKeyController.searchByApiKey(params.getValue("apikey"), Authorization)
            } else {
                ResponseEntity.badRequest().body(ErrorResponse("400 Bad Request", "Provided search parameter is invalid"))
            }
        }
    }

    @DeleteMapping("")
    fun deleteApiKeys(@RequestParam(required = false) params: Map<String, String>?,
                   @RequestHeader(required = false) Authorization: String?) : ResponseEntity<Any> {
        // Params are optional but are used to search
        return if (params == null || params.isEmpty()) {
            apiKeyController.deleteAll(Authorization)
        } else {
            if (params.contains("name")) {
                apiKeyController.deleteByName(params.getValue("name"), Authorization)
            } else if (params.contains("apikey")) {
                apiKeyController.deleteByApiKey(params.getValue("apikey"), Authorization)
            } else {
                ResponseEntity.badRequest().body(ErrorResponse("400 Bad Request", "Provided search parameter is invalid"))
            }
        }
    }

    @PostMapping("")
    fun create(@RequestParam(required = true) name: String) : ResponseEntity<Any> {
        return apiKeyController.create(name)
    }
}