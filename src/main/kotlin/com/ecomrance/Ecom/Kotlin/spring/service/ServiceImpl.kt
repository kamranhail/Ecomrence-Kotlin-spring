package com.ecomrance.Ecom.Kotlin.spring.service

import com.ecomrance.Ecom.Kotlin.spring.exceptions.ApiException
import com.ecomrance.Ecom.Kotlin.spring.exceptions.ResourceNotFoundException
import com.ecomrance.Ecom.Kotlin.spring.modal.Catagory
import com.ecomrance.Ecom.Kotlin.spring.payload.CatagoryDTO
import com.ecomrance.Ecom.Kotlin.spring.payload.CatagotyResponse
import com.ecomrance.Ecom.Kotlin.spring.repositories.CatagoryRepositoy
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class ServiceImpl(private val catagoryRepository: CatagoryRepositoy):CatagoryService {
  //  val catagoryRepository=CatagoryRepositoy()
   // var catagaries = mutableListOf<Catagory>()
    private val idGenerator = AtomicLong(1)
    @Autowired
    lateinit var modelMapper:ModelMapper
    override fun getAllcatagories(pageNumber: Int, pageSize: Int): CatagotyResponse {

        val pageDetail: Pageable = PageRequest.of(pageNumber, pageSize)

        // 2. Pass pageDetail to the repository (findAll must accept Pageable)
        val categoryPage = catagoryRepository.findAll(pageDetail)
        val categories = categoryPage.content // Extract the list from the Page object

        // 3. Validation
        if (categories.isEmpty()) {
            throw ApiException(message = "please add category first")
        }

        // 4. Map entities to DTOs
        val categoryDTOs = categories.map { category ->
            modelMapper.map(category, CatagoryDTO::class.java)
        }

        // 5. Return as your Response object
//        return listOf(CatagotyResponse(categoryDTOs,
//            pageNumber = pageNumber,pageSize=pageSize,
//
//        ))

        // 4. Create the Response object using Page metadata
        val response = CatagotyResponse(
            catagoryDTOs = categoryDTOs,
            pageNumber = categoryPage.number,       // from Page object
            pageSize = categoryPage.size,           // from Page object
            totalElement = categoryPage.totalElements, // from Page object
            totalPages = categoryPage.totalPages,   // from Page object
            lastPage = categoryPage.isLast          // from Page object
        )

        // 5. Return as a List as per your function signature
        return response
//in java it is  catagoryrespoce.setcontent


//        val categories = catagoryRepository.findAll()
//
//        if (categories.isEmpty()) {
//            throw ApiException(message = "please add category first")
//        }
//
//        val categoryDTOs = categories.map {
//            modelMapper.map(it, CatagoryDTO::class.java)
//        }
//        return listOf(
//            CatagotyResponse(categoryDTOs)
//        )
   }

    override fun createCatagory(catagorydto: CatagoryDTO): CatagoryDTO {
//        val newCatagory = catagory.copy(catagory_id = idCounter)
//        idCounter++  // increment for next category
//        catagories.add(newCatagory)

         if(catagoryRepository.findBycatagoryName(catagorydto.catagoryName)!=null)
             throw
              ApiException(
                  message = "${catagorydto.catagoryName}    already exists!"
             )

        // ✅ DTO → Entity
        val categoryEntity = modelMapper.map(
            catagorydto,
            Catagory::class.java
        )

        // ✅ Save Entity
        val savedCategory = catagoryRepository.save(categoryEntity)

        // ✅ Entity → DTO (or ResponseDTO)
        return modelMapper.map(
            savedCategory,
            CatagoryDTO::class.java
        )


//         }else {
//             ///
//             //val newCatagory = catagory.copy(catagory_id = idGenerator.getAndIncrement())
//             //  catagoryRepository.save(newCatagory)
//             val newCatagory = CatagoryDTO(
//                 catagoryId = null, // let DB assign ID
//                 catagoryName = catagorydto.catagoryName
//             )
//
//             catagoryRepository.save(newCatagory)
//
//         }
     //  catagaries.add(catagory)
    }

    override fun deleteCatagory(id: Long): Boolean {

        return if (catagoryRepository.existsById(id)) {

            // 2. If it exists, perform the deletion
            catagoryRepository.deleteById(id)

            // 3. Return TRUE to indicate successful deletion
            true
        } else {
            throw ResourceNotFoundException("Catagory", "Catagory ID", id)
            // 4. If it doesn't exist, return FALSE
            false
        }
    }


    override fun updateCatagory(id: Long, updatedCatagorydto: CatagoryDTO) {
        // 1. Find the index of the existing category

        val existingCatagory = catagoryRepository.getById(id)

        // ✅ DTO → Entity
        val categoryEntity = modelMapper.map(
            updatedCatagorydto,
            Catagory::class.java
        )
        if (existingCatagory != null) {
            // 2. If found, update the properties of the managed entity.
            // We assume the Catagory entity properties (like catagory_name) are 'var'.
            existingCatagory.catagoryName = categoryEntity.catagoryName

            // 3. Save the managed entity. Since it has an ID, JPA performs an SQL UPDATE.
            catagoryRepository.save(existingCatagory)

        } else {
            // 4. If not found, throw the custom exception.
            throw ResourceNotFoundException("Catagory", "Catagory ID", id)
           // throw CategoryupdateException("Category with ID $id not found for update.")
        }


    }

}