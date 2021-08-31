package ru.yole.jkid.examples.simple

import org.junit.Test
import ru.yole.jkid.JsonExclude
import ru.yole.jkid.JsonName
import ru.yole.jkid.deserialization.deserialize
import ru.yole.jkid.serialization.serialize
import kotlin.test.assertEquals

data class Person(
    val name: String,
    val age: Int
)

data class Person2(
    @JsonName("alias") val firstName: String,
    @JsonExclude val age: Int? = null
)

class PersonTest {
    @Test
    fun test() {
        val person = Person2("Alice", 29)
        println()

        val json = serialize(person)

        println(json)
        println(deserialize<Person2>(json))


        println()

    }
}