package chapter9

/** 실체화한 타입 파라미터를 사용하면 인라인 함수 호출에서 타입 인자로 쓰인 구체적인 타입을 실행 시점에 알 수 있다.
 * (일반 클래스나 함수의 경우 타입 인자 정보가 실행 시점에 사라지기 때문에 이런 일이 불가능하다.)
 * 선언 지점 변성을 사용하면 기저 타입은 같지만 타입 인자가 다른 두 제네릭 타입 Type<A>와 Type<B>가 있을 때
 * 타입 인자 A와 B의 상위/하위 타입 관계에 따라 두 제네릭 타입의 상위/하위 타입 관계가 어떻게 되는지 지정할 수 있다.
 *
 * List<Any> 타입에 List<Int>타입의 값을 전달 할 수 있는 여부를 선언 지점 변성을 통해 지정.

 * 사용 지점 변성은 같은 목표(제네릭 타입 값 사이의 상위/하위 타입 관계 지정)를 제네릭 타입 값을 사용하는 위치에서
 * 파라미터 타입에 대한 제약을 표시하는 방식으로 달성한다.
 * 자바 와일드카드는 사용 지점 변성에 속하며, 코틀린 선언 지점 변성과 같은 역할을 한다.
 */


/**
 * 제네릭스를 사용하면 타입 파라이터를 받는 타입을 정의할 수 있다.
 * 제네릭 타입의 인스턴스를 만들려면 타입 파라미터를 구체적인 타입 인자로 치환해야 한다.
 * 예를 들어 List 라는 타입이 있다면 그 안에 들어가는 원소의 타입을 안다면 쓸모가 있다. 타입 파라미터를 사용하면
 * '이 변수는 리스트다'라고 말하는 대신 정확하게 '이 변수는 문자열을 담는 리스트다'라고 말 할 수 있다.
 * 또 다른 예롤 Map<K, V>형태의 제네릭 맵 컬렉션이 있다면 Map<String, Person>처럼 구체적인 타입을 타입 인자로 넘기면
 * 타입을 인스턴스화 할 수 있다.
 *
 * 참고로 자바와 달리 코틀린에서는 제네릭 타입의 타입 인자를 포르그래머가 명시하거나 컴파일러가 추론 할 수 있어야 한다.
 * 자바는 늦게 도입했고, 코틀린은 처음부터 도입했기 때문에 타입을 지원하지 않고 제네릭 타입의 타입 인자를 항상 정의해야 한다.
 */


/** 제네릭 함수 호출하기 */
fun <T> List<T>.slice(indices: IntRange): List<T> {
    if (indices.isEmpty()) return emptyList()
    return this.subList(indices.first, indices.last + 1).toList()
}

fun <T> List<T>.filter(predicates: (T) -> Boolean): List<T> {
    val arrayList = ArrayList<T>()
    for(e in this) {
        if (predicates(e)) {
            arrayList.add(e)
        }
    }
    return arrayList
}
/* 제네릭 타입의 변수도 됨 단!! 확장 프로퍼티만 제네릭하게 만들 수 있다. */
val <T> List<T>.penultimate: T
get() = this[size - 2] // listOf(1,2,3,4) 라면 3이 나오겠지. 여기서 타입 파라미터는 자동으로 Int로 추론됨.

/** 타입 파라미터는 확장 프로퍼티에서만 제네릭하게 만들 수 있다.
 * 프로퍼티에 여러 타입의 값을 저장할 수는 없으므로 제네릭한 일반 프롤퍼티는 말이 안됨.*/


/** 자바와 마찬가지로 클래스 뒤에 <>를 붙이면 클래스를 제네릭하게 만들 수 있다. */

interface TestList<T> {
    operator fun get(index: Int): T
}

/** 제네릭 클래스를 확장하는 클래스를 정의하려면 기반 타입의 제네릭 파라미터에 대해 타입 인자를 지정해야 한다.
 * 이때 구체적인 타입을 넘길 수도 있고, 타입 파라미터로 받은 타입을 넘길수도 있다. */
class StringList: TestList<String> {
    val strList = emptyList<String>()

    override fun get(index: Int): String = strList[index] // 구체적인 타입 인자로 String을 지정해 List를 구현한다.
}

class TestArrayList<T>: TestList<T> {
    val list = emptyList<T>()

    override fun get(index: Int): T = list[index] // ArrayList의 타입 파라미터 T를 상위 클래스의 타입 인자로 넘긴다.
}


/** 참고로 클래스가 자기 자신을 타입 인자로 참조할 수도 있다. Comparable 인터페이스를
 * 구현하는 클래스가 이런 패터의 예다. */
interface TestCompatable<T> {
    fun compareTo(ohter: T): Int
}

/** String 클래스는 제네릭 Comparable 인터페이스를 구현하면서 그 인터페이스의 타입 파라미터 T로 String 자신을 지정 */
class TestString : TestCompatable<String> {
    override fun compareTo(other: String): Int {
        return -1
    }
}


/** 타입 파라미터 제약(type parameter constraint)은 클래스나 함수에 사용할 수 있는 타입 인자를 제한하는 기능이다.
 * 예를 들어 List의 모든 원소의 합을 구하는 sum 함수를 생각해보면 List<Int>나 List<Double>에 그 함수를 적용할 수 있지만
 * List<String>등에는 그 함수를 적용할 수 없다. sum함수가 타입 파라미터로 숫자 타입만을 허용하게 정의하면 이런 조건을
 * 표현할 수 있다.
 *
 * 어떤 타입을 제네릭 타입의 타입 파라미터에 대한 상한(upper bound)으로 지정하면 그 제네릭 타입을 인스턴스화할 때
 * 사용하는 타입 인자로 반드시 그 상한 타입이거나 그 상한 타입의 하위 타입이어야 한다. (이해하기 쉽게 하위 타입을
 * 하위 클래스와 동의어라고 생각하고 보자)
 * :: 즉, 상한 타입으로 지정하면 상한 타입 또는 상한 타입의 하위 타입만 올 수 있다는 의미.
 *
 * 제약을 걸려면 : 뒤에 타입을 작성하면 됨.
 * 자바에서는 <T extends Number>T sum(List<T> list)처럼 extends를 써서 같은 개념을 표현함.
 */

fun <T: Number> oneHalf(value: T): Double { // Number를 타입 파라미터 상한으로 설정
    return value.toDouble() // Number클래스에 정의된 메소드를 호출
}

/** 타입 파라미터를 제약하는 함수 선언하기 : 더 큰 수 찾기*/
fun <T: Comparable<T>> max(first: T, second: T): T {
    return if (first > second) first else second
}

/** 타입 파라미터에 여러 제약 걸기
 * 타입 인자가 CharSequence 또는 Appendable 인터페이스를 반드시 구현해야 한다는 사실을 표현
 * 이는 데이터에 접근하는 연산(endsWith)과 데이터를 변환하는 연산(append)을 T 타입의 값에게 수행할 수 있다는 뜻 */
fun <T> ensureTrailingPeriod(seq: T)
        where T : CharSequence, T : Appendable { // 타입 파라미터 제약 목록
    if (seq.endsWith('.').not()) { // CharSequence 인터페이스의 확장 함수를 호출
        seq.append('.') // Appendable 인터페이스의 메서드를 호출
    }
}


/** 널이 될 수 있는 타입을 포함하는 어떤 타입으로 타입 인자를 지정해도 타입 파라미터를 치환할 수 있다.
 * 아무런 상한을 정하지 않은 타입 파라미터는 결과적으로 Any?를 상한으로 정한 파라미터와 같다.
 */

class Processor<T> {
    fun process(value: T) {
        value?.hashCode()
    }
}

/** 만약 여기서 항상 널을 받을 수 없는 타입을 받으려면 제약을 가해야 한다. */

class Processor2<T: Any> {
    fun process(value: T) {
        value.hashCode()
    }
}



fun main() {
    val nullableStringProcessor = Processor<String?>()
    nullableStringProcessor.process(null)
}

