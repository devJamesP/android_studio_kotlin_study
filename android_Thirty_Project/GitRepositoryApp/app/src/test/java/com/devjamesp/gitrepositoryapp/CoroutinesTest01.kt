package com.devjamesp.gitrepositoryapp

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutinesTest01 {

    @Test
    fun test01() = runBlocking {
        var time = measureTimeMillis {
            val firstName = getFirstName()
            val lastName = getLastName()
            println("Hello, $firstName$lastName")
        }
        println("time = ${time / 1000}s")
    }

    @Test
    fun test02() = runBlocking {
        var time = measureTimeMillis {
            val firstName = async { getFirstName() }
            val lastName = async { getLastName() }
            println("Hello, ${firstName.await()}${lastName.await()}")
        }
        println("time = ${time / 1000}s")
    }

    suspend fun getFirstName(): String {
        delay(1000)
        return "박"
    }

    suspend fun getLastName(): String {
        delay(1000)
        return "지훈훈"
    }
}