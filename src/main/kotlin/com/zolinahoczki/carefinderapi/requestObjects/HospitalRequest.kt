package com.zolinahoczki.carefinderapi.requestObjects

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor


// Request Object
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class HospitalRequest {
    var name: String? = null
}