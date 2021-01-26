package com.atulmgupta.service

import org.springframework.stereotype.Service

@Service
class PersonServiceImpl : PersonService {
    override fun sign() = println("WELCOME TO KOTLIN")
}