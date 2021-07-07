package kotlincontents.collections

/**
 * 컬렉션에서 대응되는 처음과 마지막 element를 반환합니다.
 * 조건자를 사용할 수도 있습니다. 만약 조건자를 주면 해당 조건에 맞는 first, last element를 반환합니다.
 * (find, findLast 기능과 동일해짐)
 */


/**
 * firstOrNull, lastOrNull 함수들은 위와 동일한 방식으로 동작하지만 한가지 다른점은
 * 매칭되는 element가 없다면 null를 반환합니다.
 */
fun main() {
//
//    val first = numbers.first()
//    val last = numbers.last()
//
//    val firstEven = numbers.first { it % 2 == 0 }
//    val lastOdd = numbers.last { it % 2 == 1 }
//
//    println(first)
//    println(last)
//
//    println(firstEven)
//    println(lastOdd)

    /* 만약 조건자를 주어서 first, last함수를 호출하는데 조건에 부합한 원소가 없다면 error가 발생합니다. */

    val words = listOf("foo", "bar", "baz", "faz")
    val empty = emptyList<String>()

    val first = empty.firstOrNull()
    val last = empty.lastOrNull()

    val firstF = words.firstOrNull { it.startsWith('f') }
    val firstZ = words.firstOrNull { it.startsWith('z') }
    val lastF = words.lastOrNull { it.endsWith('f') }
    val lastZ = words.lastOrNull { it.endsWith('z') }

    println(first)
    println(last)

    println(firstF)
    println(firstZ)
    println(lastF)
    println(lastZ)

}

