package kotlincontents.collections

/**
 * zip 함수는 주어진 2개의 컬렉션을 하나의 새로운 컬렉션으로 합치는 것입니다.
 * 기본적으로, 동일한 인덱스를 가진 요소는 pair형태의 데이터로 되어있습니다.
 * 그러나 해당 구조는 정의할 수 있습니다.
 */

fun main() {
    val A = listOf("a", "b", "c")
    val B = listOf(1, 2, 3, 4)

    val resultPairs = A zip B
    val resultReduce = A.zip(B) { a, b -> "$a$b"}

    println(resultPairs)
    println(resultReduce)
}