package com.ecomrance.Ecom.Kotlin.spring.service

import com.ecomrance.Ecom.Kotlin.spring.exceptions.ApiException
import com.ecomrance.Ecom.Kotlin.spring.exceptions.ResourceNotFoundException
import com.ecomrance.Ecom.Kotlin.spring.modal.Catagory
import com.ecomrance.Ecom.Kotlin.spring.payload.CatagotyResponse
import com.ecomrance.Ecom.Kotlin.spring.repositories.CatagoryRepositoy
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class ServiceImpl(private val catagoryRepository: CatagoryRepositoy):CatagoryService {
  //  val catagoryRepository=CatagoryRepositoy()
   // var catagaries = mutableListOf<Catagory>()
    private val idGenerator = AtomicLong(1)

    override fun getAllcatagories(): List<CatagotyResponse> {


        val categories = catagoryRepository.findAll()

        if (categories.isEmpty()) {
            throw ApiException(message = "please add catagory first")
        }

        return categories
    }

    override fun createCatagory(catagory: Catagory) {
//        val newCatagory = catagory.copy(catagory_id = idCounter)
//        idCounter++  // increment for next category
//        catagories.add(newCatagory)

         if(catagoryRepository.findBycatagoryName(catagory.catagoryName)!=null){

              throw
              ApiException(
                  message = "${catagory.catagoryName}    already exists!"
             )
         }else {
             ///
             //val newCatagory = catagory.copy(catagory_id = idGenerator.getAndIncrement())
             //  catagoryRepository.save(newCatagory)
             val newCatagory = Catagory(
                 catagoryId = null, // let DB assign ID
                 catagoryName = catagory.catagoryName
             )

             catagoryRepository.save(newCatagory)

         }
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


    override fun updateCatagory(id: Long, updatedCatagory: Catagory) {
        // 1. Find the index of the existing category

        val existingCatagory = catagoryRepository.getById(id)

        if (existingCatagory != null) {
            // 2. If found, update the properties of the managed entity.
            // We assume the Catagory entity properties (like catagory_name) are 'var'.
            existingCatagory.catagoryName = updatedCatagory.catagoryName

            // 3. Save the managed entity. Since it has an ID, JPA performs an SQL UPDATE.
            catagoryRepository.save(existingCatagory)

        } else {
            // 4. If not found, throw the custom exception.
            throw ResourceNotFoundException("Catagory", "Catagory ID", id)
           // throw CategoryupdateException("Category with ID $id not found for update.")
        }


    }

}