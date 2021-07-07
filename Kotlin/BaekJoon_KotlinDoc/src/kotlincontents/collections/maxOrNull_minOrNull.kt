package kotlincontents.collections

/**
 * minOrNull과 maxOrNull함수는 collection의 가장 큰 element와 작은 element를 반환한다.
 * 단, 컬렉션이 비어있는 경우 null을 return한다.
 */

fun main() {
    val numbers = listOf(1, 2, 3)
    val empty = emptyList<Int>()
    val only = listOf(3)
//
//    println("Numbers: $numbers, min = ${numbers.minOrNull()} max = ${numbers.maxOrNull()}")
//    println("Empty: $empty, min = ${empty.minOrNull()}, max = ${empty.maxOrNull()}")
//    println("Only: $only, min = ${only.minOrNull()}, max = ${only.maxOrNull()}")
}