package kotlincontents.productivity_boosters

/**
 * String templates을 사용하면 변수를 참조하고, 표현식을 문자열에 포함 할 수 있습니다.
 */

fun main() {
    val greeting = "Kotliner"

    println("Hello $greeting")                  // 1
    println("Hello ${greeting.toUpperCase()}")  // 2
}