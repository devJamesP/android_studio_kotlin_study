package kotlincontents.functional

/*
코틀린 확장 메커니즘에 따라 클래스에 새로운 멤버를 추가해보자. 즉,
2개의 타입 확장이 있다: 확장 함수와 확장 프로퍼티.
일반적인 함수와 프로퍼티들과 비슷해보이지만 한가지 중요한 점이 다르다.
확장하게되면 타입을 지정해주어야한다.
 */

data class Item(val name : String, val price : Float) // 1

data class Order(val items : Collection<Item>)

//fun Order.maxPricedItemValue(): Float = this.items.maxBy { it.price }?.price ?: 0f // 2
//fun Order.maxPricedItemName() : String = this.items.maxBy { it.price }?.name ?: "NO_PRODUCTS" // 3

val Order.commaDelimitedItemNames: String
    get() = this.items.joinToString { it.name }

fun main() {
    val order = Order(listOf(Item("Bread", 25.0f), Item("Wine", 29.0f), Item("Water", 12.0f)))

//    println("Max priced item name: ${order.maxPricedItemName()}")        // 4
//    println("Max priced item value: ${order.maxPricedItemValue()}")
    println("Items: ${order.commaDelimitedItemNames}")                   // 5

    val a = listOf(1, 2, 3,4, 5)
}