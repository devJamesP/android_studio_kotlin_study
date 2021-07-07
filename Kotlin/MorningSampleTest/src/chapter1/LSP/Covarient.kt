package chapter1.LSP

interface Cage<T> {
    fun get(): T
}

open class Animal

open class Hamster(var name: String) : Animal()

class SuperHamster(name: String) : Hamster(name)

fun tamingHamster(cage: Cage<out Hamster>) {
    println("길들이기: ${cage.get().name}")
}

fun ancestorOfHamster(cage: Cage<in Hamster>) {
    println("ancestor = ${cage.get()}")
}

fun matingSuperHamster(cage: Cage<SuperHamster>) {
    val hamster = SuperHamster("stew")
    println("교배 : ${hamster.name} & ${cage.get().name}")
}

fun main() {
    val animal = object : Cage<Animal> {
        override fun get(): Animal {
            return Animal()
        }
    }

    val hamster = object : Cage<Hamster> {
        override fun get(): Hamster {
            return Hamster("Hamster")
        }
    }

    val superHamster = object : Cage<SuperHamster> {
        override fun get(): SuperHamster {
            return SuperHamster("Leo")
        }
    }

    tamingHamster(hamster)
    tamingHamster(superHamster)

    ancestorOfHamster(animal)
    ancestorOfHamster(hamster)

    matingSuperHamster(superHamster)
}
