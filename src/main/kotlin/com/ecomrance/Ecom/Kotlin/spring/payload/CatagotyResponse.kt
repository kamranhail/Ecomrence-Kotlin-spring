package com.ecomrance.Ecom.Kotlin.spring.payload




class CatagotyResponse(val catagoryDTOs: List<CatagoryDTO>,
                     val  pageNumber:Int,
                       val   pageSize:Int,
                       val  totalElement: Long,
                       val   totalPages:Int,
                       val lastPage:Boolean
    ) {
}