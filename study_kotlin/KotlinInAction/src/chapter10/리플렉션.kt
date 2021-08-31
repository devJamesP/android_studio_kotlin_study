package chapter10

import kotlin.reflect.full.memberProperties


/** KClass는 자바 java.lang.Class 타입과 같은 역할을 하는 코틀린 타입이다. 코틀린 클래스에 대한 참조를 저장할 때
 * KClass 타입을 사용한다.
 * KClass의 타입 파라미터는 이 KClass의 인스턴스가 가리키는 코틀린 타입을 지정한다.
 * 예를 들어 CompanyImple::class의 타입은 KClass<CompanyImple>이다.
 * 이 타입은 방금 본 DeserializeInterface의 파라미터 타입인 KClass<out Any>의 하위 타입이다.
 * 공변성을 추가하지 않으면 CompanyImpl::class를 인자로 넘길 수 없고 오직 Any::class만 넘길 수 있다.
 * out 키워드가 있으면 모든 코틀린 타입 T에 대해 KClass<T>가 KClass<out Any>의 하위 타입이 된다.(공변성)
 * 따라서 Deserialize Interface의 인자로 Any뿐 아니라 Any를 확장하는 모든 클래스에 대한 참조를 전달 할 수 있다.
 */


/*
제이키드 라이브러리 중 @CustomSerializer 애노테이션이 있다.
해당 애노테이션의 구현은 다음과 같다.
annotation class CustomSerializer(
val serializerClass : KClass<out ValueSerializer<*>>
)
 */


/** CustomSerializer가 ValueSerializer를 구현하는 클래스만 인자로 받아야 함을 명시할 필요가 있다.
 * 예를 들어 Date가 ValueSerializer를 구현하지 않으므로 @CustomSerializer(Date::class)라는 애노테이션을 금지시켜야 한다.
 */

/// 따라서...!! KClass<out ValueSerializer<*>로 함으로써 올바른 인자를 받아들이면서
/// ValueSerializer를 사용해 어떤 타입의 값이든 직렬화할 수 있게 허용한다.(스타 프로젝션으로 타입 표현)

/** 조금 난해하더라도 비슷한 패턴으로 작성 할 수 있다.
 * KClass<out 허용할 클래스 이름>을 사용한다. 만약 제네릭 클래스를 인자로 받아야 한다면
 * 허용할 클래스 이름 뒤에 <*>를 붙이면 된다.
 */


/** 리플렉션(reflection) */
/* 리플렉션은 실행 시점에 (동적으로) 객체의 프로퍼티와 메소드에 접근할 수 있게 해주는 방법이다. */
/** 보통 객체의 메소드나 프로퍼티에 접근할 때는 프로그램 소스코드 안에 구체적인 선언이 있는 메소드나
 * 프로퍼티 이름을 사용하며, 컴파일러는 그런 이름이 실제로 가리키는 선언을 컴파일 시점(정적으로) 찾아내서 해당하는 선언이
 * 실제 존재함을 보장한다. 하지만 타입과 관계없이 객체를 다뤄야 하거나 객체가 제공하는 메소드나 프로퍼티 이름을
 * 오직 실행 시점에만 알 수 있는 경우가 있다.
 * Json 직렬화 라이브러리가 그런 경우다
 * 직렬화 라이브러리는 어떠한 객체든 직렬화가 가능해야 하고, 실행 시점이 되기 전까지 라이브러리가 직렬화할 프로퍼티나
 * 클래스에 대한 정보를 알 수 없다. 이런 경우 리플렉션을 사용해야 한다.
 */


/** 코틀린에서 리플렉션을 사용하려면 두 가지 서로 다른 리플렉션 API를 다뤄야 한다.
 * 1. 자바가 java.lang.reflect 패키지를 통해 제공하는 표준 리플렉션
 * 코틀린 클래스가 일반적인 자바 바이트코드로 컴파일되므로 자바 리플렉션 API도 코틀린 클래스를
 * 컴파일한 바이트코드를 완벽 지원함.
 * 2. kotlin.reflect 패키지를 통해 제공하는 리플렉션 api
 * 코틀린 리플렉션은 자바 리플렉션을 완벽히 대항 할 수 있지는 않음.
 */

/** 코틀린 리플렉션 API : KClass, KCallable, KFunction, KProperty
 * java.lang.Class에 해당하는 KClass를 사용하면 클래스 안에 있는 모든 선언을 열거하고
 * 각 선언에 접근하거나 클래스의 상위 클래스를 얻는 등의 작업이 가능하다.
 * MyClass::class라는 식을 쓰면 KClass의 인스턴스를 얻을 수 있다. (생각해보면, MYClass::class.java 이 표현이 자바 리플렉션 이었음)
 * 실행 시점에 객체의 클래스를 얻으려면 먼저 객체의 javaClass 프로퍼티를 사용해 객체의 자바 클래스를 얻어야 한다.
 * javaClass는 자바의 java.lang.Object.getClass()와 같다.
 */

//class Person4(val name: String, val age: Int)
//
//fun main() {
//    val person = Person4("Alice", 29)
//    val kClass = person.javaClass.kotlin // KClass<Person>의 인스턴스를 반환
//    println(kClass.simpleName)
//    kClass.memberProperties.forEach { println(it.name) }
//}

/** 이런식으로 클래스에 들어있는 프로퍼티 이름을 출력하고,
 * 해당 클래스와 모든 조상 클래스 내부에 정의된 비확장 프로퍼티를 모두 가져옴. */

class Person4(val name: String, val age: Int)

fun main() {
    val person = Person4("Alice", 29)
    val kClass = person.javaClass.kotlin // KClass<Person>의 인스턴스를 반환
    println(kClass.simpleName)
    kClass.memberProperties.forEach { println(it.name) }
}

/**
 * 참고로 클래스의 모든 멤버는 KCallable 인스턴스의 컬렉션이고, 이는 함수와 프로퍼티를 아우르는
 * 공통 상위 인터페이스다. 그 안에는 call 메서드가 들어있다. call을 사용하면 함수나 프로퍼티의 게터를 호출 할 수 있다.
 */