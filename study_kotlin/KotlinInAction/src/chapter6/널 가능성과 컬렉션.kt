package chapter6

import java.io.BufferedReader
import java.lang.NumberFormatException

/** 컬렉션에서 널 다루기 */
fun readMunbers(reader: BufferedReader): List<Int?> {
    val result = ArrayList<Int?>()
    for (line in reader.lineSequence()) {
        val number = line.toIntOrNull()
        result.add(number)
    }
    return result
}

fun addValidNumbers(numbers: List<Int?>) {
    val validNumbers = numbers.filterNotNull()
    println("Sum of valid numbers: ${validNumbers.sum()}")
    println("Invalid numbers : ${numbers.size - validNumbers.size}")
}


/**
 * 예를 들어 파라미터로 Collection 인터페이스 객체를 수신한다면
 * 해당 컬렉션은 읽기 전용으로 볼 수 있으면 만약 원본 컬렉션을 수정하려면
 * MutableCollection으로 수신하면 된다. 또한 방어적 복사(defendenct copy)를 통해 원본 변경을 막을 수도 있다.
 */
fun <T> copyElements(source: Collection<T>, target: MutableCollection<T>) {
    for(item in source) {
        target.add(item)
    }
}

/** 꼭 근대 컬렉션을 참조하고 있는 인스턴스의 타입이 읽기 전용일 필요는 없다.
 * 다만 다른 레퍼런스 변수에서 해당 컬렉션을 사용하고 있는 도중
 * 또 다른 레퍼런스 변수가 해당 컬렉션을 변경한다면
 *  ConcurrentModificationException오류가 발생 할 수 있다. 따라서
 *  읽기 전용 컬렉션이 항상 Thread Safe하지 않다는 점을 명심할 것!!
 */


fun main() {
val strings = listOf("a", "b", "c")
    println("%s/%s/%s".format(*strings.toTypedArray()))
}