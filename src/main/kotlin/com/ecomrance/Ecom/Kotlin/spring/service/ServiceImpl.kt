package com.ecomrance.Ecom.Kotlin.spring.service

import com.ecomrance.Ecom.Kotlin.spring.modal.Catagory
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class ServiceImpl:CatagoryService {
    var catagaries = mutableListOf<Catagory>()
    private val idGenerator = AtomicLong(1)

    override fun getAllcatagories(): List<Catagory> {
        return catagaries
    }

    override fun createCatagory(catagory: Catagory) {
//        val newCatagory = catagory.copy(catagory_id = idCounter)
//        idCounter++  // increment for next category
//        catagories.add(newCatagory)


        val newCatagory = catagory.copy(catagory_id = idGenerator.getAndIncrement())
        catagaries.add(newCatagory)
     //  catagaries.add(catagory)
    }

    override fun deleteCatagory(id: Long): Boolean {
        return catagaries.removeIf { it.catagory_id == id }
    }

    override fun updateCatagory(id: Long, updatedCatagory: Catagory) {
        // 1. Find the index of the existing category
        val index = catagaries.indexOfFirst { it.catagory_id == id }

        if (index >= 0) {
            // 2. If found, replace the item at that index.
            catagaries[index] = updatedCatagory.copy(catagory_id = id)
        } else {
            // 3. If not found, DO NOTHING (silent failure).
            // This prevents the exception from propagating up to the controller.
            println("Category with ID $id not found. Update skipped.")
        }
    }
}