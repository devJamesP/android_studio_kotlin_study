package kotlincontents.scope_fuctions

/**
 * let 처럼 run도 코틀린 표준 라이브러리 범위 기반 함수입니다. 기본적으로, let과 유사합니다.
 * 블록 내부의 코드를 실행하고 마지막 표현식을 반환합니다.
 * 두 함수의 차이점은 run함수를 호출한 호출자(객체)를 this로 접근 가능합니다. 이게 무슨말이냐?
 * 내부적으로 let은 람다식 내부의 인자로 호출자(객체)를 명시적으로 넘기지만,
 * run은 내부적으로 람다식 내부의 인자로 run함수를 호출한 객체의 확장 함수 형태(묵시적)로 인자를 넘깁니다.
 * 따라서 자기 자신이 곧 인자가 되는 셈이죠.(this임 -> 생략 가능함.)
 * run함수는 이럴때 사용합니다. :: null을 체크하고, 멤버 메소드를 여러개 호출하며, 어떤 값을 연산하거나
 * 여러 개의 로컬변수의 범위를 제한하고자 할 때 사용합니다.
 * it으로 참조하는 것 보다 더욱 편리합니다. :) let의 기능과 with의 기능을 합쳐놓은 느낌??
 *
 */

fun main() {
    getNullableLength(null)
    getNullableLength("")
    getNullableLength("some string with Kotlin")
}
fun getNullableLength(ns: String?) {
    println("for \"$ns\":")
    // isEmpty() 메소드 혹은 length 프로퍼티 호출 시 편하게 호출 할 수 있음.
    ns?.run {
        println("\tis empty? " + isEmpty())
        println("\tlength = $length")
        length
    }
}