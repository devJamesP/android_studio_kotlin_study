package kotlincontents.kotlin_js

/**
 * dynamic은 Kotlin/JS의 특별한 타입입니다. 기본적으로 코틀린 type checker가 동작되지 않습니다.
 * 이것은 타입이 지정되지 않거나, 환경과 상호운용 하기 위해 필요합니다. JavaScript의 생태계와 같은 경우
 */

fun main() {
//    val a: dynamic = "abc"                                               // 1
//    val b: String = a                                                    // 2
//
//    fun firstChar(s: String) = s[0]
//
//    println("${firstChar(a)} == ${firstChar(b)}")                        // 3
//
//    println("${a.charCodeAt(0, "dummy argument")} == ${b[0].toInt()}")   // 4
//
//    println(a.charAt(1).repeat(3))                                       // 5
//
//    fun plus(v: dynamic) = v + 2
//
//    println("2 + 2 = ${plus(2)}")                                        // 6
//    println("'2' + 2 = ${plus("2")}")
}
/**
1. 모든 값을 동적 변수 유형에 할당 할 수 있습니다.
2. 동적 값은 무엇이든 할당 할 수 있습니다.
3. 동적 변수는 모든 함수에 인수로 전달할 수 있습니다.
4. 인수가있는 모든 속성 또는 함수는 동적 변수에서 호출 할 수 있습니다.
5. 동적 변수에 대한 함수 호출은 항상 동적 값을 반환하므로 호출을 연결할 수 있습니다.
6. 연산자, 할당 및 인덱싱 된 액세스 ([..])는 "있는 그대로"번역됩니다. 조심하세요!
 */