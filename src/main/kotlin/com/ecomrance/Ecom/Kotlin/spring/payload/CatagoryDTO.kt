package com.ecomrance.Ecom.Kotlin.spring.payload

import jakarta.validation.constraints.NotBlank
// this is  request object
//catagory DTO is used to encapsulateo
// data and transfer to clint to server
class CatagoryDTO(
    var catagoryId: Long? = null,

    var catagoryName: String=""
) {
}