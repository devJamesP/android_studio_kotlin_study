package kotlincontents.collections

/**
 * find와 findLast 함수는 조건이 주어졌을 때 조건에 매칭하여 처음과 마지막 element를 반환합니다.
 * 조건에 맞는 element가 단 한개도 없다면 null을 반환합니다.
 */



fun main() {
    val words = listOf("Lets", "find", "something", "in", "collection", "somehow")

    val first = words.find { it.startsWith("some") }
    val last = words.findLast { it.startsWith("some") }

    val nothing = words.find { it.contains("nothing")}

    println(words)

    println(first)
    println(last)
    println(nothing)
}