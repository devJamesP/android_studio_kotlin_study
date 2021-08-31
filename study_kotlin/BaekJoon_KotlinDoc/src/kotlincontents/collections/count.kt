package kotlincontents.collections

/*
count
count함수는 조건자에 맞는 elements의 수를 반환하거나 elements 전체의 수를 반환합니다.
 */

fun main() {
    val numbers = listOf(1, -2, 3, -4, 5, -6)

    val totalCount = numbers.count()
    val evenCount = numbers.count { e ->
        e % 2 == 0
    }
    println(evenCount)
}