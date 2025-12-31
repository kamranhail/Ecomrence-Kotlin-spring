package com.ecomrance.Ecom.Kotlin.spring.Controlar

import com.ecomrance.Ecom.Kotlin.spring.modal.Catagory
import com.ecomrance.Ecom.Kotlin.spring.service.CatagoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/k")
class CatagoryContraller (private val catagoryService: CatagoryService) {


    @GetMapping("/public/catagories")
    fun getAllCatagories(): List<Catagory> {
        return catagoryService.getAllcatagories()
    }


    @PostMapping("/public/catagories")
    //@RequestMapping(value = "/api/k/public/catagories", method = RequestMethod.POST)
    fun createCatagory(@Valid@RequestBody catagory: Catagory): ResponseEntity<String> {
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
        catagory.catagoryId = idd

        // 2. Call the service layer. The service will handle the existence check internally.
        catagoryService.updateCatagory(idd, catagory)

        // 3. Always return 200 OK.
        // NOTE: This is incorrect REST practice if the ID doesn't exist, as it should be 404.
        return ResponseEntity.ok("Category with ID $idd update request processed.")



}
}

//..In Spring Boot, ResponseEntity is a powerful tool that represents the entire HTTP response. Think of it as a "box" where you can pack three things to send back to the user:

//The Body: The actual data (JSON, String, etc.).

//The Status Code: (e.g., 200 OK, 404 Not Found, 400 Bad Request).

//The Headers: Metadata (e.g., Content-Type, custom security tokens).