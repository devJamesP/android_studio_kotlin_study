package chapter10

import kotlin.reflect.*
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

/** 애노테이션과 리플렉션을 사용하면 꼭 그 함수가 정의된 클래스의 이름과 함수 이름, 파라미터 이름등을 알지 못하더라도
 * 그러한 제약을 벗어나서 임의의 클래스를 다룰 수 있다.
 * 애노테이션을 사용하면 라이브러리가 요구하는 의미를 클래스에게 부여할 수 있고, 리플렉션을 사용하면
 * 실행 시점에 컴파일러 내부 구조를 분석할 수 있다.
 *
 * 자바 프레임워크도 에노테이션을 많이 사용한다. 코틀린 에노테이션도 개념은 마찬가지다. 메타데이터를 선언에 추가하면
 * 애노테이션을 처리하는 도구가 컴파일 시점이나 실행 시점에 적절한 처리를 해준다.
 * */


/** 애노테이션 적용
 * 애노테이션은 @과 애노테이션 이름으로 이뤄진다. 함수나 클래스 등 여러 다른 코드 구성 요소에 애노테이션을 붙일 수 있다.
 * 예를 들어 제이유닛 JUnit 프레임워크를 사용한다면 테스트 메서드 앞에 @Test 애노테이션을 붙여야 한다.
 */
//class MyTest {
//    @Test fun testTrue() { // @Test 애노테이션을 사용해 제이유닛 프레임워크에게 이 메서드를 테스트로 호출하라고 지시함.
//        Assert.assertTrue(true)
//    }
//}

/** @Deprecated의 경우 코틀린과 자바가 동일한데 코틀린의 경우 replaceWith 파라미터를 통해 옛 버전을 대신할 수 있는
 * 패턴을 제시 할 수 있음. 그리고 그걸 보고 새 버전으로 쉽게 포팅할 수 있음.
 */
@Deprecated("Use removeAt(index) instead.", ReplaceWith("remove(index)"))
fun remove(index: Int) { } // 이런식으로 ~

/** 이런 remove 함수 선언이 있다면 인텔리J 아이디어는 remove를 호출하는 코드에 대해 경고 메시지를 표시해 줄 뿐 아니라
 * 자동으로 그 코드를 새로운 API 버전에 맞는 코드로 바꿔주는 퀵 픽스(quick fix)도 제시해준다.
 * 애노테이션 인자로는 원시 타입의 값, 문자열 , enum 클래스 참조, 다른 애노테이션 클래스가 들어갈 수 있다.
  */


/** 애노테이션 인자를 지정하는 방법
 * 1. 클래스를 애노테이션 인자로 지정할 때는 @MyAnnotation(MyClass::class)처럼 ::class를 클래스 이름 뒤에 넣어야 한다.
 * 2. 다른 애노테이션을 인자로 지정할 때는 인자로 들어가는 애노테이션의 이름 앞에 @를 넣지 않아야 한다.
 * 예를 들어 ReplaceWith는 애노테이션인데, Deprecated의 애노테이션의 인자로 들어가므로 ReplaceWith앞에 @를 사용하지 않음.
 * 3. 배열을 인자로 지정하려면 @RequestMapping(path = arrayOf("/foo", "/bar"))처럼 arrayOf 함수를 사용함.
 * 자바에서 선언한 애노테이션 클래스를 사용한다면 value라는 이름의 파라미터가 필요에 따라 자동으로 가변 길이 인자로 변환.
 * 따라서 그런 경우에는 @JavaAnnotationWithArrayValue("abc", "foo", "bar")처럼 arrayOf 함수를 쓰지 않아도 됨.
 */

/** 또한 애노테이션은 컴파일 시점에 인자가 무엇인지 알 수 있어야 한다.
 * 프로퍼티를 애노테이션 인자로 사용하려면 그 앞에 const 변경자를 붙여야 한다. 컴파일러는 const가 붙은 프로퍼티를
 * 컴파일 시점 상수로 취급한다.
 */

//const val TEST_TIMEOUT = 100L
//@Test(timeout = TEST_TIMEOUT) fun testMethod() { ... }

/** 코틀린 클래스의 프로퍼티를 주 생성자에 넣으면
 * 해당 프로퍼티의 변경 가능성 여부를 고려하여 자바의 게터와 세터에 대응한다.
 * 사용 지점 대상 (use site target)선언으로 애노테이션을 붙일 요소를 정할 수 있다.
 * 사용 지점 대상은 @기호와 애노테이션 이름 사이에 붙으며, 애노테이션 이름과는 콜론(:)으로 분리된다.
 */

/* 예제 get은 @Rule 애노테이션을 프로퍼티 게터에 적용하라는 뜻 */
//@get:Rule :: 사용 지점 대상 / 애노테이션 이름

/** 규칙을 지정하려면 public field나 method 앞에 @Rule을 붙여야 한다. 하지만 코틀린 프로퍼티 앞에 @Rule을 붙이면
 * 제이유닛 예외가 발생하는데 이유는 코틀린의 필드가 기본적으로 비공개이기 때문이다. 따라서 @Rule 애노테이션을 정확한
 * 대상에 적용하려면 @get:Rule을 사용해야 한다.
 */


/** 자바에 선언된 애노테이션을 사용해 프로퍼티에 애노테이션을 붙이는 경우 기본적으로 프로퍼티의 필드에
 * 그 애노테이션이 붙는다. 하지만 코틀린으로 애노테이션을 선언하면 프로퍼티에 직접 적용할 수 있는 애노테이션을
 * 만들 수 있다.
 *
 * < 사용 지점 대상 지정시 지원하는 대상 목록 >
 *     property : 프로퍼티 전체, 자바에서 선언된 애노테이션에는 이 사용 지점 대상을 사용할 수 없다.
 *     field : 프로퍼티에 의해 생성되는 필드
 *     get : 프로퍼티 게터
 *     set : 프로퍼티 세터
 *     receiver : 확장 함수나 프로퍼티의 수신 객체 파라미터
 *     param : 생성자 파라미터
 *     setparam  : 세터 파라미터
 *     delegate : 위임 프로퍼티의 위임 인스턴스를 담아둔 필드
 *     file : 파일 안에 선언된 최상위 함수와 프로퍼티를 담아두는 클래스
 */


/** file 대상을 사용하는 애노테이션은 package 선언 앞에서 파일의 최상위 수준에만 적용할 수 있다. 파일에 흔히
 * 적용하는 애노테이션으로는 파일에 있는 최상위 선언을 담는 클래스의 이름을 바꿔주는 @JvmName이 있다.
 * @file:JvmName("StringFunctions")
 *
 *
 * 자바와 달리 코틀린에서는 애노테이션 인자로 클래스나 함수 선언이나 타입 외에 임의의 식을 허용한다.
 * 가장 흔히 쓰이는 예로는 컴파일러 경고를 무시하기 위한 @Suppress 애노테이션이 있다.
 * 다음은 안전하지 못한 캐스팅 경고를 무시하는 로컬 변수 선언이다.
 */
fun test(list: List<*>) {
    @Suppress("UNCHECKED_CAST")
    val strings = list as List<String>
}


/*
자바 API를 애노테이션으로 제어하기
- 코틀린은 코틀린으로 선언한 내용을 자바 바이트코드로 컴파일하는 방법과 코틀린 선언을 자바에 노출하는 방법을 제어하기
위한 애노테이션을 많이 제공한다. 이런 애노테이션 중 일부는 자바 언어의 일부 키워드를 대신한다.
@Volatile, @Strictfp 애노테이션은 자바의 volatile과 strictfp 키워드를 그대로 대신함.
 */

// @JvmName은 코틀린 선언이 만들어내는 자바 필드나 메서드 이름을 변경한다.
fun foo (a: List<String>) {
    println("foo(a : List<String>")
}

@JvmName("foo2")
fun foo (a: List<Int>) {
    println("foo(a : List<String>")
}

/* 자바 프로그래밍에서 시그니처라 하면 메서드 시그니처를 의미하고, 해당 시그니처의 항목에는
메서드 명, 파라미터 순서, 파라미터 타입, 갯수가 포함된다.
즉, 코틀린 메서드를 자바에서 사용 할 때 JVM시그니처가 동일하면 error가 날 수 있기 때문에
@JvmName annotation을 사용해서 JVM시그니처를 다르게 표기 할 수 있다.
(제네릭 타입은 컴파일 타임에 구별되지 않으므로 같은 List로 인식 함
 */

// @JvmStatic은 메소드, 객체 선언, 동반 객체에 적용하면 그 요소가 자바 정적 메서드로 노출된다.

// @JvmOverloads를 사용하면 디폴트 파라미터 값이 있는 함수에 대해 컴파일러가 자동으로 오버로딩한 함수를 생성해 줌.

// @JvmField를 프로퍼티에 사용하면 게터나 세터가 없는 public 자바 필드로 프로퍼티를 노출 시킴.


/** 애노테이션을 활용한 JSON 직렬화 제어
 * 애노테이션을 사용하는 고전적이 예제로 객체 직렬화 제어가 있다.
 * 직렬화(Serialization)는 객체를 저장장치에 저장하거나 네트워크를 통해 전송하기 위해 텍스트나 이진 형식으로
 * 변환하는 것이다. 반대 과정인 역직렬화(Deserialization)는 텍스트나 이진 형식으로 저장된 데이터로부터 원래의
 * 객체를 만들어낸다. 직려화에 자주 쓰이는 형식에 JSON이 있다. 자바와 JSON을 변환할 때 자주 쓰이는 라이브러리로는
 * 잭슨(Jakson)과 GSON(지슨)이 있다.
 */


/** 애노테이션 선언 440p
 * @JsonExclude 애노테이션(제이키드 라이브러리에 있는 애노테이션)은 굉장히 단순한 애노테이션이다.
 * 애노테이션 선언 구문은 일반 클래스 선언처럼 보이며, 일반 클래스와의 차이는 class 키워드앞에
 * annotation이라는 변경자가 붙어있다는 점 뿐이다.
 * 하지만 애노테이션 클래스는 오직 선언이나 식과 관련 있는 메타데이터(metadata)의 구조를 정의하기 때문에
 * 내부에 아무 코드도 들어있을 수 없다. 그런 이유로 컴파일러는 애노테이션 클래스에서 본문을 정의하지 못하게 막는다.
 * 파라미터가 있는 애노테이션을 정의하려면 애노테이션 클래스의 주 생성자에 파라미터를 선언하면 된다.

 * 일반 클래스의 주 생성자 선언 구문을 똑같이 사용한다. 다만, 애노테이션 클래스에서는 모든 파라미터 앞에 val을 붙여야만 한다.
 */

/* 코틀린
annotation class JsonName(val name: String)
 */

/* 자바
public @interface JsonName {
    String value();
*/
/** 코틀린 애노테이션에서는 name이라는 프로퍼티를 사용했지만 자바에서는 value라는 메서드를 사용했다.
 * 자바에서 value 메서드는 특별한데, 어떤 애노테이션을 적용할 때 value를 제외한 모든 애트리뷰트에는 이름을 명시해야 한다.
 * 반면 코틀린의 애노테이션 적용 문법은 일반적인 생성자 호출과 같다. 따라서 인자의 이름을 명시하기 위해
 * 이름 붙인 인자 구문을 사용할 수도 있고 이름을 생략할 수도 있다.
 * 제이키드 예제에서 Person data 클래스의 firstName에 @JsonName(name = "first_name")은 @JsonName("first_name")과 같다.
 * 자바에서 선언한 애노테이션을 코틀린의 구성 요소에 적용할 때는 value를 제외한 모든 인자에 대해 이름 붙인 인자 구문을 사용해야 한다.
 *
 * 정리하면?
 * 자바에서 선언한 애노테이션을 코틀린 구성요소에 적용 할 때 value를 제외한 모든 인자에 대해 이름 붙인 인자 구문을 사용해야 한다는 의미
 */


/** 메타애노테이션: 애노테이션을 처리하는 방법 제어
 * 애노테이션 클래스에 적용 할 수 있는 애노테이션을 메타애노테이션(meta-annotation)이라고 부른다.
 * 표준 라이브러리에 있는 메타애노테이션 중 가장 흔히 쓰이는 메타애노테이션은 @Target이다.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class JsonExclude // 이러면 JsonExclude 애노테이션은 프로퍼티에만 사용 할 수 있는 사용 선언 지점이 설정 된 것.

/* @Target 메타애노테이션은 애노테이션을 적용할 수 있는 요소의 유형을 지정한다.
애노테이션 클래스에 대해 구체적인 @target을 지정하지 않으면 모든 선언에 적용할 수 있는 애노테이션이 된다.
 */

/** 애노테이션이 붙을 수 있는 대상이 정의된 이넘(enum)은 AnnotationTarget 이다.
 * 그 안에는 클래스, 파일, 프로퍼티, 프로퍼티 접근자, 타입, 식 등에 대한 이넘 정의가 들어있다.
 * 필요하다면 @Target(AnnotationTarget.CLASS, AnnotationTarget.METHOD)처럼
 * 둘 이상의 대상을 한꺼번에 선언할 수도 있다.
 */

/* 메타애노테이션을 직접 만들려면 다믕과 같이 ANNOTATION_CLASS를 대상으로 지정 */
@Target (AnnotationTarget.ANNOTATION_CLASS)
annotation class BindingAnnotation
@BindingAnnotation
annotation class MyBinding


/** @Retention 애노테이션
 * 자바 @Retention 애노테이션은 정의 중인 애노테이션 클래스를 소스 수준에서만 유지할지 .class 파일에 저장할지
 * 실행 시점에 리플렉션을 사용해 접근할 수 있게 할지를 지정하는 메타애노테이션이다.
 * 자바 컴파일러는 기본적으로 애노테이션을 .class 파일에는 저장하지만 런타임에는 사용할 수 없게 한다.
 * 하지만 대부분의 애노테이션은 런타임에도 사용할 수 있어야 하므로 코틀린에서는 기본적으로 애노테이션의 @Retention을
 * RUNTIME으로 지정한다.
 * 즉, 코틀린에서는 @Retention애노테이션을 사용하여 RUNTIME으로 설정하고 대부분을 사용.
 */




/** 클래스 참조를 인자로 받는 방법을 보면 다음과 같다
 * 제이키드 라이브러리 DeserializeInterface 참고!! */
/*
<예제 참고>
interface Company {
    val name: String
}

data class CompanyImpl(override val name: String) : Company

data class Person(
        val name: String,
        @DeserializeInterface(CompanyImpl::class) val company: Company
)

class DeserializeInterfaceTest {
    @Test fun test() = testJsonSerializer(
            value = Person("Alice", CompanyImpl("JetBrains")),
            json = """{"company": {"name": "JetBrains"}, "name": "Alice"}"""
    )
}

////////////////////////////
@Target(AnnotationTarget.PROPERTY)
annotation class DeserializeInterface(val targetClass: KClass<out Any>)
////////////////////////////
 */


fun foo2(x: Int) = println(x)
//fun main() {
//    val kFunction = ::foo2
//    kFunction.call(42)
//    kFunction.call() // 여기서 error : call에 넘긴 인자 개수완 원래 함수에 정의된 파라미터 개수가 맞아 떨어져야 함.
//}
/**
 * ::foo2 식의 값 타입이 리플렉션 API에 있는 KFunction 클래스의 인스턴스임을 알 수 있다.
 * 이 함수 참조가 가리키는 함수를 호출하려면 KCallable.call 메소드를 호출한다.
 */


//fun sum(x : Int, y: Int) = x + y
//fun main() {
//    val kFunction: KFunction2<Int, Int, Int> = ::sum  // 최상위에 임포트 해야함
//    println(kFunction.invoke(1, 2) + kFunction(3, 4))
//}
/** kFunction의 invoke 메소드를 호출할 때는 인자 개수나 타입이 맞아 떨어지지 않으면 컴파일이 안 된다.
 * 따라서 KFunction의 인자 타입과 반환 타입을 모두 다 안다면 invoke 메소드를 호출하는 게 낫다.
 * call 메소드는 모든 타입의 함수에 적용할 수 있는 일반적인 메소드지만 타입 안전성을 보장해주지는 않는다.
 */

/** 451p KFunctionN인터페이스는 언제, 어떻게 정의 될까?
 * KFunction1과 같은 타입은 파라미터 개수가 다른 여러 함수를 표현한다. 각 KFunctionN타입은 KFunction을 확장하며,
 * N과 파라미터 개수가 같은 invoke를 추가로 포함한다. 예를 들어 KFunction2<P1, P2, R>에는
 * operator fun invoke(p1: P1, p2: P2): R 선언이 들어있다.
 * 이런 함수는 컴파일러가 생성한 합성 타입(syntheic comiler-generated type)이다. 따라서
 * kotlin-reflect 패키지에서 이런 타입의 정의를 찾을 수는 없다.
 * 코틀린에서는 컴파일러가 생성한 합성 타입을 사용하기 때문에 원하는 수만큼 많은 파라미터를 갖는 함수에 대한
 * 인터페이스를 사용할 수 있다. 합성 타입을 사용하기 때문에 코틀린은 kotlin-runtime.jar의 크기를
 * 줄일 수 있고, 함수 파라미터 개수에 대한 인위적인 제약을 피할 수 있다.
 */

/* KProperty의 call메소드는 프로퍼티의 게터를 호출한다. 하지만 프로퍼티 인터페이스는 프로퍼티 값을 얻는 더 좋은
방법으로 get 메소드를 제공한다.
get 메소드 접근 시 프로퍼티 선언에 따라 올바른 인터페이스를 사용해야 함.
최상위 프로퍼티는 KProperty() 인터페이스의 인스턴스로 표현되며, KProperty()안에는 인자가 없는 get 메소드가 있음.
 */
data class Person5(val name: String, val age: Int)

var counter = 0
val person = Person5("Alice", 25)

fun main() {
    val kProperty = ::counter
    kProperty.setter.call(21) // 리플렉션 기능을 통해 세터 호출하면서 21을 인자로 넘김
    println(kProperty.get()) // get을 호출해 프로퍼티 값을 가져옴.

    val memberProperty = Person5::age
    println(memberProperty.get(person))
}
/** KProperty는 제네릭 클래스로 memberProperty변수는 KProperty<Person, Int>타입으로,
 * 첫 번째 타입 파라미터는 수신 객체 타입, 두 번째 타입 파라미터는 프로퍼티 타입을 표현함.
 * 따라서 수신 객체를 넘길 때는 KProperty1의 타입 파라미터와 일치해야함.
 *
 * 참고로 최상위 수준이나 클래스 안에 정의된 프로퍼티만 리플렉션으로 접근 가능함.(로컬은 안됨)
 */


/* KClass, KFunction, KParameter는 모두 KAnnotatedElement를 확장한다.
KClass는 클래스와 객체를 표현할 때 쓰이고, KProperty는 모든 프로퍼티를 표현할 수 있고, 그 하위 클래스인
KMutableProperty는 var로 정의한 변경 가능한 프로퍼티를 표현함. KProperty와 KMutableProperty에 선언된
Getter와 Setter인터페이스로 프로퍼티 접근자를 함수처럼 다룰 수 있다.
 */

//private fun StringBuilder.serializeObject(obj: Any) {
//    val kClass = obj.javaClass.kotlin // 객체의 KClass
//    val properties = kClass.memberProperties // 클래스의 모든 프로퍼티
//
//    properties.joinToStringBuilder(
//        this, prefix = "{", postfix = "}") { prop ->
//        serializeString(prop.name) // 프로퍼티 이름을 얻음
//        append(": ")
//        serializePropertyValue(prop.get(obj)) // 프로퍼티 값을 얻음
//    }
//}   // 제이키드 라이브러리임. 결과 : {propertyName: propertyValue} 형태
//

/** 커스텀 직렬화기 */
private fun StringBuilder.serializeProperty(
    prop: KProperty1<Any, *>, obj: Any
) {
    val name = prop.findAnnotation<JsonName>()?.name ?: prop.name
    serializeString(name)
    append(": ")
    val value = prop.get(obj)
    val jsonValue =
        prop.getSerializer()?.toJsonValue(value) ?: value
    serializePropertyValue(jsonValue)+----

}