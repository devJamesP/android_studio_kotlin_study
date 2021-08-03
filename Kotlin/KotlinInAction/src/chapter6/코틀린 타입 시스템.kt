package chapter6

import java.lang.IllegalArgumentException


/** Unit타입은 함수형 프로그래밍에서 전통적으로 '단 하나의 인스턴스만 갖는 타입을 의미'하며
 * Nothing은 함수가 정상적으로 끝나지 않는다는 사실을 알게 해주기 위해 특별한 반환 타입이다.
 * Nothing타입은 아무 값도 포함하지 않으며, 반환 타입이나 반환 타입으로 쓰일 타입 파라미터로만 쓸 수 있다.
 * 그 외의 용도로 사용하는 경우 아무 값도 저장하지 않으므로 아무 의미 없다.
 * Nothing을 반환하는 함수를 엘비스 연산자의 우항에 사용해서 전제 조건(Precondition)을 검사할 수 있다.
 */

fun fail(message: String) : Nothing {
    throw IllegalArgumentException(message)
}

data class Company(val address : String?, val name : String?)

fun main() {
    val company = Company(null, null)
    val address = company.address ?: fail("No address")
    println(address) // 컴파일러는 이부분부터 address가 null이 아님을 안다!!!

    /* 위처럼 타입 시스템에서 Nothing이 유용하다
    * 컴파일러는 Nothing이 반환 타입인 함수가 결코 정상 종료되지 않음을 알고
    * 그 함수를 호출하는 코드를 분석할 때 사용한다.
    * 컴파일러는 예제에서 company.address가 널인 경우 엘비스 연산자의 우항에서
    * 예외가 발생한다는 사실을 파악하고 address의 값이 널이 아님을 추론할 수 있다.*/
}

