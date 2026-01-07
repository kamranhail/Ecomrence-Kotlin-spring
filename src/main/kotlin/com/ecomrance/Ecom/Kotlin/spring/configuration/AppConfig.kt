package com.ecomrance.Ecom.Kotlin.spring.configuration

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AppConfig {

//    @Bean
//    val ModalMapper modalMapper()

    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }


    // Using a property with a getter
//    @get:Bean
//    val modelMapper: ModelMapper
//        get() = ModelMapper()

//    Compact Interview Questions
//    Q1: What is the benefit of using properties over functions in Kotlin configuration?
//
//    Answer: It makes the configuration class look cleaner and more declarative. It treats the Bean as a "component" of the configuration rather than an "action" to be performed.
//
//    Q2: Can you inject other beans into a property-style bean?
//
//    Answer: It’s harder. If your ModelMapper needed a custom configuration bean, it’s much easier to use a function because you can simply pass the dependency as a parameter: fun modelMapper(config: MyConfig).
//
//    Q3: How does Kotlin's val relate to Java's final in a configuration class?
//
//    Answer: A Kotlin val is translated to a final field with a getter in Java. Spring's proxy mechanism (CGLIB) works with these getters to ensure the Singleton pattern is maintained (i.e., you always get the same instance).


//fun...Use this if you need to pass parameters (dependencies) into the bean.Propertyval
    //property...Use this for simple utility beans that don't have other dependencies.
}