package com.ecomrance.Ecom.Kotlin.spring.service

import com.ecomrance.Ecom.Kotlin.spring.modal.Catagory
import com.ecomrance.Ecom.Kotlin.spring.payload.CatagotyResponse

interface CatagoryService {

    fun getAllcatagories  ():List<CatagotyResponse>
    // before DTP
  //  fun getAllcatagories  ():List<Catagory>


    fun createCatagory(catagory: Catagory)
    fun deleteCatagory(id: Long):Boolean

    fun updateCatagory(id: Long, updatedCatagory: Catagory)

}