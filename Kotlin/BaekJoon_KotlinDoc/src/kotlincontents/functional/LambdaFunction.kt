package kotlincontents.functional
import java.util.Locale
/*
람다 함수는 아주 쉽게 함수를 생성할 수 있습니다. 람다는 묵시적 변수(it)와 타입 추론 덕분에 매우 간결하게 표시됩니다.
 */

val upperCase1: (String) -> String = { str: String -> str.toUpperCase() } // 1

val upperCase2: (String) -> String = { str -> str.toUpperCase() }         // 2

val upperCase3 = { str: String -> str.toUpperCase() }                     // 3

// val upperCase4 = { str -> str.toUpperCase() }                          // 4

val upperCase5: (String) -> String = { it.toUpperCase() }                 // 5

val upperCase6: (String) -> String = String::toUpperCase                  // 6

fun main() {
    println(upperCase1("hello"))
    println(upperCase2("hello"))
    println(upperCase3("hello"))
    println(upperCase5("hello"))
    println(upperCase6("hello"))
}
/*
1. 타입을 명시한 람다식
2. 람다식 외부에서 타입을 명시하면 람다식 내부 파라미터의 타입이 추론된다.
3. 람다식 내부 파라미터로부터 타입을 추론한다.
4. 둘다 작성 안하면 안됨.
5. 명시적으로 람다식 내의 파라미터의 이름을 작성하지 않으면 묵시적으로 it으로 접근 가능하다.
6. 람다함수 내에서 하나의 변수만을 호출한다면 :: 함수 포인터를 사용할 수 있다.
 */

fun<T> T.nullSafeToString() = this?.toString() ?: "NULL" // 1
/* 모든 객체를 string형태로 출력할 때 nullsafe하게 출력할 수 있다.!! */