package com.ecomrance.Ecom.Kotlin.spring.exceptions

// The 'message' passed here goes directly into RuntimeException
//class ApiException(message: String) : RuntimeException(message)


//2. If you want to keep Secondary Constructors
//If you must use the constructor keyword, you have to use the colon (:) to send the message to the parent (super).
//
//Kotlin

//class ApiException : RuntimeException {
//
//    // Default constructor
//    constructor() : super()
//
//    // Constructor that takes a message and passes it to super
//    constructor(message: String) : super(message)
//}


class ApiException(
    val errorCode: Long=404,
    val timestamp: Long = System.currentTimeMillis(),
    message: String
) : RuntimeException(message)