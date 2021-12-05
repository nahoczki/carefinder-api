package com.zolinahoczki.carefinderapi.responseObjects

import lombok.Value

@Value
class DetailedResponse (
    val message: String,
    val data: Any
)