package kotlincontents.functional

/*
A higher-order-function = 고차 함수
A higher-order-function은 파라미터 또는 반환 값으로써의 또다른 함수를 사용하는 함수를 말합니다.
 */

//fun calculate(x : Int, y : Int, operation : (Int, Int) -> Int) : Int { // 1
//    return operation(x ,y) // 2
//}
//
//fun sum(x: Int, y: Int) = x + y // 3
//
//fun baekjoon.main() {
//    val sumResult = calculate(1, 5, ::sum)                // 4
//    val mulResult = calculate(4, 5) { x, y -> x * y}      // 5
//    println("sumResult = $sumResult, mulResult = $mulResult")
//}

/*
1. higher-order function을 선언. 두개의 파라미터 x, y를 추가하고, 또다른 함수
operation을 파라미터로써 갖습니다. the operation parameter는 반환 타입이기도 합니다.

2. 고차함수는 operation은 인자를 받아 호출하여 결과를 반환합니다.

3. operation 이름과 매칭되는 함수를 선언합니다.

4. 고차함수 호출 시 2개의 integer values와 함수를 넘깁니다. 인자는 ::sum으로
::notation은 코틀린에서 함수의 이름을 참조할 때 사용합니다.

5. 고차함수 호출 시 함수의 인자로 람다식을 넘길 수 있습니다.
 */

fun operation() : (Int) -> Int {        // 1
    return ::square
}

fun square(x : Int) = x * x             // 2

fun main() {
    val func = operation()              // 3
    println(func(2))                    // 4
}
/*
1. 고차함수의 return값은 함수입니다. (Int) -> Int로 파라미터는 다음과 같고, return type은 square function입니다.
2. 명시된 함수와 매칭되는 함수입니다.
3. operation함수를 값에 할당합니다. func는 operation을 반환할 때 square를 리턴합니다.
4. func 함수 호출 시 square함수를 실질적으로 실행합니다.
 */