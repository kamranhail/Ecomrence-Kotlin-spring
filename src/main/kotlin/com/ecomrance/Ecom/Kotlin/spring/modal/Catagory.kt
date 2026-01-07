package com.ecomrance.Ecom.Kotlin.spring.modal

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity(name="catagories")
data class Catagory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    var catagoryId: Long? = null,
    @field:NotBlank
    var catagoryName: String=""
) {
}