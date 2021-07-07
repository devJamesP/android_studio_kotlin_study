package kotlincontents.collections

/*
any all none 함순들은 주어진 조건자와 element를 매치해주는 컬렉션의 확장을 체크해주는 함수입니다.
*/

/**
 ** any **
any 함수는 조건이 주어졌을 때 적어도 하나의 element를 포함하고 있다면 true를 반환합니다.
즉, 조건에 맞는 element가 한개라도 있다면 true라는 의미
 */

val anyNegative = numbers.any { it < 0 }             // 2
val anyGT6 = numbers.any { it > 6 }                  // 3

/**
 * all함수는 조건자를 컬렉션에 매칭하여 모든 엘리먼트가 해당 조건에 부합한다면 true를 반환합니다.
 */

val allEven = numbers.all { it % 2 == 0}
val alless6 = numbers.all { it < 6 }


/**
 * none함수는 조건이 주어졌을 때 컬렉션에 매칭하여 조건에 부합하는 엘리먼트가 단 한개도 없을 때 true를 반환합니다.
 */

val allEven_none = numbers.none { it % 2 == 1}
val alless6_none = numbers.none { it > 6 }


fun main() {
    println(anyNegative)
    println(anyGT6)

    println(allEven)
    println(alless6)

    println(allEven_none)
    println(alless6_none)
}