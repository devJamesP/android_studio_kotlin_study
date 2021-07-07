package kotlincontents.collections

/**
 * getOrElse 함수는 컬렉션의 안전한 접근을 제공한다. default 값을 제공하는 인덱스와 함수가 필요하며,
 * 만약 인덱스의 범위를 넘어설 경우 기본값을 반환합니다.
 */

fun main() {
    val list = listOf(0, 10, 20)
    println(list.getOrElse(1) { 42 })
    println(list.getOrElse(10) { 42 }) // 인덱스 10은 컬렉션의 범위를 넘어섰으므로 기본값이 42를 반환

    /** getOrElse는 키에대한 값이 주어졌을 때 map에도 적용할 수 있다. */
    val map = mutableMapOf<String, Int?>()
    println(map.getOrElse("x") { 1 })

    map["x"] = 3
    println(map.getOrElse("x") { 1 })

    map["x"] = null
    println(map.getOrElse("x") { 1 }) // 키가 존재해도 값이 null(정의되지 않음) 이므로 default값인 1이 반환!!
}