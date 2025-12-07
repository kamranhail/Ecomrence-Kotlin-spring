package com.ecomrance.Ecom.Kotlin.spring.modal

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Catagory(
    @Id
    var catagory_id: Long,
    val catagory_name: String
) {
}