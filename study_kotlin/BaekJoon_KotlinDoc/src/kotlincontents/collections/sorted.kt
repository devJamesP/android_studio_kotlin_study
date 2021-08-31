package kotlincontents.collections

import kotlin.math.abs

/**
 * sorted는 기본적으로는 오름차순으로 정렬하여 컬렉션의 리스트형태로 반환합니다.
 *
 * sortedBy는 지정한 seletor함수의 반환 값에따라 정렬합니다.
 */

// 참고로 원본 리스트는 그대로고 정렬 할 때 조건을 주면 해당 조건이 적용된 리스트를 기준으로 원본 리스트를 정렬하는 거임.
fun main() {
    val shuffled = listOf(5, 4, 2, 1, 3, -10)
    val natural = shuffled.sorted()
    val inverted = shuffled.sortedBy { -it }
    val descending = shuffled.sortedDescending()
    val descendingBy = shuffled.sortedByDescending { abs(it) }

    println(natural)
    println(inverted)
    println(descending)
    println(descendingBy)
}