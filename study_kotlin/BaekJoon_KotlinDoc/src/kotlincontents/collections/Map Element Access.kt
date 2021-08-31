package kotlincontents.collections

/**
 * 맵을 적용할 때 []operator는 주어진 키에 상응하는 값 또는 null을 반환합니다.(null의 경우 키가 없을경우)
 * getValue함수는 주어진 키에 상응하여 존재하는 값을 반환하거나 키를 찾을 수 없을 때 예외를 던집니다.
 *
 * withDefault로 maps를 만들면, getValue를 return하게 될 경우 값이 없을 때 예외를 던지지 않고 default 값을 반환합니다.
 */

fun main() {
    val map = mapOf("key" to 42)

    val value1 = map["key"]
    val value2 = map["key2"]

    val value3: Int = map.getValue("key")

    // 이 의미는 withDefault의 람다식은 키값이 존재하지 않으면 해당 키의 길이를 값으로써 defualt값을 설정한 것이다.
    val mapWithDefault = map.withDefault { k -> k.length }
    val value4 = mapWithDefault.getValue("key2")

    println(value1)
    println(value2)

    println(value3)

    println(value4)

    try {
        map.getValue("anotherKey")
    } catch (e : NoSuchElementException) {
        println("Message $e")
    }
}