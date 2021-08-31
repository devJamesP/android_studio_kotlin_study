package kotlincontents.collections

/**
 * The partition 함수는 original collection을 splits하여 pair of lists하는 함수입니다.
 * 이때 조건자가 주어집니다.
 *
 * 1. 조건에 따라 true or false :: true면 first프로퍼티, false면 second프로퍼티
 */

fun main() {
    val numbers = listOf(1, -2, 3, -4, 5, -6)

    val evenOdd = numbers.partition { it % 2 == 0 }
    val (positives, negatives) = numbers.partition { it > 0 }

    println(numbers)

    println("Even numbers: ${evenOdd.first}")
    println("Odd numbers: ${evenOdd.second}")

    println("Positive numbers: $positives")
    println("Negative numbers: $negatives")
}

