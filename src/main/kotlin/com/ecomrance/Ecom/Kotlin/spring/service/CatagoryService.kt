package com.ecomrance.Ecom.Kotlin.spring.service

import com.ecomrance.Ecom.Kotlin.spring.payload.CatagoryDTO
import com.ecomrance.Ecom.Kotlin.spring.payload.CatagotyResponse

interface CatagoryService {

    fun getAllcatagories(pageNumber: Int, pageSize: Int):CatagotyResponse
    // before DTP
  //  fun getAllcatagories  ():List<Catagory>


    fun createCatagory(catagoryDto: CatagoryDTO) : CatagoryDTO

    fun deleteCatagory(id: Long):Boolean

    fun updateCatagory(id: Long, updatedCatagorydto: CatagoryDTO)

}