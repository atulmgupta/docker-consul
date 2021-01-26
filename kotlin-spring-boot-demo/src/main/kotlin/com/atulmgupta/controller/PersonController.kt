package com.atulmgupta.controller

import com.atulmgupta.model.Greeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

@RequestMapping(value = ["/person"])
@RestController
class PersonController {

    val counter = AtomicInteger()

    @Autowired
    lateinit var restTemplate: RestTemplate

    @GetMapping(value = ["/test"])
    fun test(): ResponseEntity<String> {
//        if (theValue == 0)
//            return ResponseEntity<String>("Service is running @ " + System.currentTimeMillis(), HttpStatus.NOT_FOUND)
//        else{
            println("Waiting")

            println("Waiting done")
            return ResponseEntity<String>("Service is running @ " + System.currentTimeMillis(), HttpStatus.OK)
//        }

    }

    @GetMapping(value = ["/greeting"])
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): ResponseEntity<Greeting> {
        try {
            val respnse = restTemplate.getForEntity("http://localhost:9003/person/test", String::class.java)
            System.err.println(respnse.statusCode)
            System.err.println(respnse.statusCodeValue)
        } catch (e: Exception) {
            e.printStackTrace()
            when (e) {
                is IOException -> {
                    println("IO-EXCEPTION")
                }
                is ResourceAccessException -> {
                    println("ResourceAccessException-EXCEPTION")
                }
                is HttpStatusCodeException -> {
                    println("HttpStatusCodeException-EXCEPTION")
                }
            }
        }

        try {
            val respnse = restTemplate.getForEntity("http://localhost:9004/person/test", String::class.java)
            System.err.println(respnse.statusCode)
            System.err.println(respnse.statusCodeValue)
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    println("IO-EXCEPTION")
                }
                is ResourceAccessException -> {
                    println("ResourceAccessException-EXCEPTION")
                }
                is HttpStatusCodeException -> {
                    println("HttpStatusCodeException-EXCEPTION")
                }
            }
        }

        if (true)
            return ResponseEntity<Greeting>(
                Greeting(counter.incrementAndGet(), "Hello, $name"),
                HttpStatus.BAD_REQUEST
            );
        return ResponseEntity<Greeting>(Greeting(counter.incrementAndGet(), "Hello, $name"), HttpStatus.FOUND);
    }

}