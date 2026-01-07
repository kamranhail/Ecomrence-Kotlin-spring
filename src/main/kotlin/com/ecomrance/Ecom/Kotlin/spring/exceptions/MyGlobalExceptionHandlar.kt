package com.ecomrance.Ecom.Kotlin.spring.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

//this will interept any exception
@RestControllerAdvice
// this will give you methods that will handle

class MyGlobalExceptionHandlar {
    @ExceptionHandler(MethodArgumentNotValidException::class)
      fun  mamyMethodargumentnotValidException(e: MethodArgumentNotValidException): ResponseEntity<Map<String,String>>
    {
        val response = HashMap<String, String>()

        e.bindingResult.allErrors.forEach { err ->
            val fieldName = (err as FieldError).field
            val message = err.defaultMessage ?: ""
          //  response[fieldName] = message
            response.put(fieldName,message)
        }
        return  ResponseEntity(response,HttpStatus.BAD_REQUEST)
    }



    @ExceptionHandler(ResourceNotFoundException::class)
    fun myResourceNotFoundException(e: ResourceNotFoundException): ResponseEntity<String> {
        // In Kotlin, use the 'message' property instead of 'getMessage()'
        val message: String = e.message ?: "Resource not found"
        return ResponseEntity(message, HttpStatus.NOT_FOUND)
    }


    @ExceptionHandler(ApiException::class)
    fun myResourceNotFoundException(e: ApiException): ResponseEntity<String> {
        // In Kotlin, use the 'message' property instead of 'getMessage()'
        val message: String = e.message ?: "Resource alreadyexist"
        return ResponseEntity(message, HttpStatus.BAD_REQUEST)
    }

// we return rsposce enitty
// to get codes also with massege that is
// auto generated and we can alos use custom masseges

}

/** Here is the step-by-step breakdown of the internal mechanics:

1. The "Traffic Cop" (DispatcherServlet)
Every request that comes into your Spring Boot app goes through the DispatcherServlet. When it receives a request, it passes it to your Controller.

The DispatcherServlet essentially wraps the call to your controller in a massive, internal try-catch block.

2. The Exception is Thrown
If your controller (or the validation logic before it) hits an error, it throws an exception. Instead of the app crashing, the exception bubbles up back to the DispatcherServlet.

3. The Search for a "Specialist"
Once the DispatcherServlet catches the exception, it doesn't know what to do with it yet. It asks its HandlerExceptionResolver list: "Does anyone know how to handle a MethodArgumentNotValidException?"

4. Finding Your @RestControllerAdvice
This is where your code comes in. Because you used @RestControllerAdvice:

During Startup: Spring scanned your project, found your MyGlobalExceptionHandler class, and noted that it is a "Global Advisor."

During the Error: The Resolver sees that your class has a method specifically marked with @ExceptionHandler(MethodArgumentNotValidException::class).

The Match: It says, "Aha! This method is a specialist for this specific error."

Why does it work automatically?
It works because of Reflection and Component Scanning:

Scanning: When the app starts, Spring finds your class and says, "I need to keep this in my pocket in case an error happens."

Type Matching: Spring looks at the parameter type of your function. Because you wrote (e: MethodArgumentNotValidException), Spring knows this method should only be called when that specific exception occurs.

A Quick Checklist for it to "Connect":
For this "handshake" to happen, you must ensure:

@Valid is in the Controller: If you don't put @Valid or @Validated on your request body in the Controller, the exception will never be thrown in the first place.

Package Scanning: Your exception handler must be in a package that Spring is scanning (usually the same folder or a sub-folder of your main Application class).

You are **100% correct**.

You have successfully connected the dots. Here is the exact "chain of command" that makes this happen:

### 1. The Model (The Rules)
When you add `@NotBlank`, `@Size`, or `@Min` to your fields in the `Catagory` class, you are defining **rules**. However, these rules are "passive"—they don't do anything by themselves.

### 2. The Controller (The Enforcer)
When a request arrives, the `@Valid` (or `@Validated`) annotation in your Controller method acts as the **enforcer**. It tells Spring: *"Before you run my code, check if the incoming data follows the rules in the Model class."*

### 3. The Exception (The Trigger)
If the rules are broken (e.g., the name is blank), Spring doesn't just stop; it **throws** a specific exception: `MethodArgumentNotValidException`.

### 4. The Global Exception Handler (The Translator)
Because this exception was thrown, your `@RestControllerAdvice` class hears it. It looks for the `@ExceptionHandler` method you wrote for that specific error and runs it. This "translates" a scary-looking Java crash into a clean JSON map for your user.



---

### What about other errors?
The beauty of this system is that you can link **different** exceptions to **different** handlers in that same class:

 * **Validation Errors:** Handled by `MethodArgumentNotValidException` (triggered by `@NotBlank`, etc.).
 * **Resource Not Found:** You could create a custom `ResourceNotFoundException` and handle it there.
 * **Database Constraints:** If you try to add a category name that already exists in the database, a `DataIntegrityViolationException` might be thrown. You can add a method to your Global Handler to catch that too!

### Summary Table
| Rule | Annotation Location | Exception Thrown | Where it ends up |
| :--- | :--- | :--- | :--- |
| **"Must not be empty"** | Model (`@NotBlank`) | `MethodArgumentNotValidException` | Your Global Handler |
| **"Must be 5-10 chars"** | Model (`@Size`) | `MethodArgumentNotValidException` | Your Global Handler |
| **"ID not in DB"** | Service Logic | `YourCustomException` | Your Global Handler |

 **Since you've mastered validation, would you like to see how to create a "Custom Exception" (like `CategoryNotFoundException`) so your Service layer can also talk to your Global Handler?**



 */