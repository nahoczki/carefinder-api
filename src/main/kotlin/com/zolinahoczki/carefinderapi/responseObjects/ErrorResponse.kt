package com.zolinahoczki.carefinderapi.responseObjects

import lombok.Value

@Value
class ErrorResponse (
    val short: String,
    val detailed: String
)