package chapter9

import java.lang.IllegalArgumentException

/** JVM의 제네릭스는 보통 타입 소거(type ensure)를 사용해 구현된다.
 * 이는 실행 시점에 제네릭 클래스의 인스턴스에 타입 인자 정보가 들어있지 않다는 뜻이다.
 * inline으로 선언함으로써 이런 제약을 어떻게 우회할 수 있는지를 본다.
 * inline으로 만들면 타입 인자가 지워지지 않게 할 수 있다. (이를 reify :: 실체화 라고 부름)
 */



/** 자바와 마찬가지로 코틀린 제네릭 타입 인자 정보는 런타입에 지워진다. 이는 제네릭 클래스 인스턴스가
 * 그 인스턴스를 생성할 때 쓰인 타입 인자에 대한 정보를 유지하지 않는다는 뜻이다.
 * 예를 들어 List<String> 객체를 만들고 그 안에 문자열을 여럿 넣더라도 실행 시점에는 그 객체를
 * 오직 List로만 볼 수 있다.
 * 가령 List<String>, List<Int> 객체를 생성하더라도, 컴파일러는 두 리스트를 서로 다른 타입으로 인식하지만
 * 실행 시점에 그 둘은 완전히 같은 타입의 객체다. 즉, 단지 List일 뿐이다.
 * 그럼에도 불구하고 보통은 List<String>에는 문자열만 들어있고, List<Int>에는 정수만 들어있다고 가정할 수 있는데,
 * 이는 컴파일러가 타입 인자를 알고 올받른 타입의 값만 각 리스트에 넣도록 보장해주기 때문이다. (자바의
 * raw 타입을 사용해 리스트에 접근하고 타입 캐스트를 활용하면 컴파일러를 속일 수는 있지만 폼이 많이 듬.)
 */


/** * 다음으로 타입 소거로 인해 생기는 한계를 알아보자
 * 타입 인자를 따로 저장하지 않기 때문에 실행 시점에 타입 인자를 검사할 수 없다. 예를 들어 어떤 리스트가 문자열로 이뤄진
 * 리스트인지 다른 객체로 이뤄진 리스트인지를 실행 시점에 검사할 수 없다. is 검사에서 타입 인자로 지정한 타입을 검사할 수는
 * 없다.
 * if (value is List<String>) { ... } => 컴파일 오류 남. (실행 시점에 List<String>타입이 지워진다고 error 뜸.)
 * 대신 저장해야 하는 타입 정보의 크기가 줄어들어서 전반적인 메모리 사용량이 줄어든다는 제네릭 타입 소거 나름의 장점이 있음.
 */


/**
 * 코틀린에서는 타입 인자를 명시하지 않고 제네릭 타입을 사용할 수 없다. 그렇다면 어떤 값이 집합이나 맵이 아니라
 * 리스트라는 사실을 어떻게 확인할 수 있을까? 바로 스타 프로젝션(star projection)을 사용
 *  타입 파라미터가 2개 이상이라면 모든 타입 파라미터에 *를 포함시켜야 한다. (자바의 List<?>와 비슷)
 *  앞선 예제에서 value가 List이지만 타입까지는 알 수 없다.
 *  as, as? 캐스틍에도 제네릭 타입을 사용할 수 있지만, 기저 클래스가 같더라도 타입이 다른경우, 이 경우도 캐스팅이 되기 떄문에
 *  조심해야한다. 즉,!!! 제네릭 타입의 타입 인자를 알 수 없으므로 타입 캐스팅에 성공한다. 그런 타입 캐스팅을 사용하면
 *  컴파일러가 unchecked cast라고 경고를 한다. 하지만 컴파일러는 단순 경고만 하고 컴파일을 진행한다.
  */

/** 396p, 제네릭 타입으로 타입 캐스팅하기 */
fun printSum(c: Collection<*>) {
    val intList = c as? List<Int>
        ?: throw IllegalArgumentException("List is expected")
    println(intList.sum())
}
/** 만약 잘못된 타입으로 캐스팅을 하게 되면 ClassCastException이 발생함 */


/** 다시 한번 말하지만 제네릭 타입은 실행 시점에 지워진다. 제네릭 클래스의 인스턴스가
 * 있어도 그 인스턴스를 만들 때 사용한 타입 인자를 알아낼 수 없다. 제네릭 함수의 타입 인자도 마찬가지로
 * 제네릭 함수가 호출되도 그 함수의 본문에서는 호출 시 쓰인 타입 인자를 알 수 없다.
 *
 * 이런 제약을 피하기 위해 inline을 사용하면 된다. 실행 시점에 inline함수의 타입 파라미터는 실체화하므로 타입 인자를 알 수 있다.
 * inline 함수는 컴파일러가 그 함수식을 모두 함수 본문으로 바꾸기 때문에
 * 함수가 람다를 인자로 사용하는 경우 그 함수를 인라인 함수로 만들면 람다 코드도 함께 인라이닝되고, 그에 따라
 * 무명 클래스와 객체가 생성되지 않아서 성능도 좋아진다.
 */

/** 399p 실체화한 타입 파라미터를 사용하는 함수 정의 */
//fun <T> isB(value: Any) = value is T // 컴파일 에러 남
/** is 캐스팅은 제네릭 타입에 대한 검사를 금지하고, as 캐스팅은 경고 메시지 띄움!! */
inline fun <reified T> isA (value: Any) = value is T // 이제는 이 코드가 컴파일 됨!!

/** filterIsInstance 표준 라이브러리 함수 사용 */
// val items = listOf("one", 2, "three")
// items.filterIsInstance<String>()

/** 이렇게 하면 이 함수의 반환 타입은 따라서 List<String>이다. 여기서는 타입 인자를
 * 실행 시점에 알 수 있고 filterIsInstance는 그 타입 인자를 사용해 리스트의 원소 중에
 * 타입 인자와 타입이 일치하는 원소만은 추려낼 수 있다.
 */

/** 400p filterIsInstance를 간단하게 정리한 버전 */
inline fun <reified T>
        Iterable<*>.testFilterIsInstance(): List<T> {
    val destination = mutableListOf<T>()
    for (element in this) {
        if (element is T) destination.add(element)
    }
    return destination
}

//fun main() {
//    val mixedList = listOf(1, 2.0, "삼", 4.0f, 5L)
//    val strList = mixedList.testFilterIsInstance<String>()
//    val intList = mixedList.testFilterIsInstance<Int>()
//
//    println(strList)
//    println(intList)
//}

/** 실체화한 타입 인자는 어떻게 작동할까? 왜 일반 함수에서는 element is T를 쓸 수 없고
 * 인라인 함수에서만 쓸 수 있는 걸까? 이유는 컴파일러는 인라인 함수의 본문을 구현한 바이트코드를 그 함수가
 * 호출되는 모든 지점에 삽입한다. 컴파일러는 실체화한 타입 인자를 사용해 인라인 함수를 호출하는
 * 각 부분의 정확한 타입 인자를 알 수 있다. 따라서 컴파일러는 타입 인자로 쓰인 구체적인 클래스를 참조하는
 * 바이트코드를 생성해 삽입할 수 있다. 결과적으로 filterIsInstance<String> 호출은 다음과 동등한 코드를 만들어냄.
 * for (element in this) {
 *      if (element is String) {
 *          destination.add(element)
 *      }
 */

