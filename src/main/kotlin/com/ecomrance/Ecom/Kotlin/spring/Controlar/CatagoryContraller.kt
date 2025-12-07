package com.ecomrance.Ecom.Kotlin.spring.Controlar

import com.ecomrance.Ecom.Kotlin.spring.modal.Catagory
import com.ecomrance.Ecom.Kotlin.spring.service.CatagoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.yaml.snakeyaml.events.Event.ID

@RestController
@RequestMapping("/api/k")
class CatagoryContraller (private val catagoryService: CatagoryService) {


    @GetMapping("/public/catagories")
    fun getAllCatagories(): List<Catagory> {
        return catagoryService.getAllcatagories()
    }


    @PostMapping("/public/catagories")
    //@RequestMapping(value = "/api/k/public/catagories", method = RequestMethod.POST)
    fun createCatagory(@RequestBody catagory: Catagory): ResponseEntity<String> {
        catagoryService.createCatagory(catagory)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Category added successfuly")
    }

    @DeleteMapping("/public/catagories/{idd}")
    fun deleteCatagory(@PathVariable idd: Long): ResponseEntity<String> {
        // Attempt to delete the category
        val isDeleted = catagoryService.deleteCatagory(idd)

        return if (isDeleted) {
            // 204 No Content: Standard response for a successful DELETE
            // when the response body is intentionally empty.
            ResponseEntity.noContent().build()
        } else {
            // 404 Not Found: If the service returns false, we assume the category
            // with the given ID did not exist to be deleted.
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Category with ID $idd not found.")
        }
    }


    @PutMapping("/public/catagories/{idd}")
    fun updateCatagory(@PathVariable idd: Long,
                       @RequestBody catagory: Catagory): ResponseEntity<String> {
        // Attempt to delete the category
        // 1. Set the ID onto the received Catagory object
        catagory.catagory_id = idd

        // 2. Call the service layer. The service will handle the existence check internally.
        catagoryService.updateCatagory(idd, catagory)

        // 3. Always return 200 OK.
        // NOTE: This is incorrect REST practice if the ID doesn't exist, as it should be 404.
        return ResponseEntity.ok("Category with ID $idd update request processed.")



}
}