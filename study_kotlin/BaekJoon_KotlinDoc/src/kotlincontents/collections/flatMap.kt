package kotlincontents.collections

/**
 * flatMap은 iterable object(반복 가능한 객체)로 각각의 컬렉션의 element를 변형한다. 그리고 하나의 list로 반환한다.
 * 변형은 사용자가 정의해야 합니다.
 */

fun main() {
    val fruitsBag = listOf("apple", "orange", "banana", "grapes")
    val clothesBag = listOf("shirts", "pants", "jeans")
    val cart = listOf(fruitsBag, clothesBag)
    val mapBag = cart.map { it }
    val flatMapBag = cart.flatMap { it }
    val flatMapBag2 = cart.flatten()

    println(fruitsBag)
    println(clothesBag)
    println(cart)
    println(mapBag)
    println(flatMapBag)
    println(flatMapBag2)

}