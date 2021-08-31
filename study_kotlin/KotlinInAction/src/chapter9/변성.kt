package chapter9

import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

/**
 * 변성 개념은 List<String>와 List<Any>와 같이 기저 타입이 같고 타입 인자가 다른 여러 타입이 서로 어떤 관계가 있는지 설명하는 개념이다.
 * 원소 변경이 없는 경우 List<String> -> List<Any>의 타입에 넘겨도 되지만 인자로 받은 변경 가능한 리스트에 원소를 추가 할 경우
 * 타입이 일치해야 한다.
 */

//fun printContents(list: List<Any>) {
//    println(list.joinToString())
//}
//
//fun addAnswer(list: MutableList<Any>) {
//    list.add(42)
//}
//
//fun main() {
//    printContents(listOf("asdf", "dfdsa"))
//    val strings = mutableListOf("asdf", "fdsa")
//    addAnswer(strings) <- Error
//    println(strings)
//}
/** 즉, 변경 불가능한 타입 파라미터를 받는 것은 안전하다고 볼 수 있지만 변경 가능한 타입의 경우 타입 불일치가 생길 수 있으므로 */


/** 406p 타입과 클래스는 다르다. 제네릭 클래스가 아닌 클래스에서는 클래스 이름을 바로 타입으로 쓸 수 있다. 예를 들어
 * var x: String이라고 쓰면 String클래스의 인스턴스를 저장하는 변수를 정의할 수 있다. 하지만 var x: String?처럼 같은 클래스 이름을
 * 널이 될 수 있는 타입에도 쓸 수 있다는 점을 기억해야 한다. 이는 모든 코틀린 클래스가 적어도 둘 이상의 타입을 구성 할 수 있다는
 * 의미이다.
 *
 *
 * 제네릭 클래스에서는 상황이 복잡하다. 올바른 타입을 얻으려면 제네릭 타입의 타입 파라미터를 구체적인 타입 인자로 바꿔줘야 한다.
 * 예를 들어 List는 타입이 아니다. 하지만 타입 인자를 치환한 List<Int>, List<String?>, List<List<String>>등은 모두 제대로 된 타입이다.
 * 각각의 제네릭 클래스는 무수히 많은 타입을 만들어 낼 수 있다.
 * 타입 사이의 관계를 논하기 위해 하위 타입(subtype)이라는 개념을 잘 알아야 한다.
 * 어떤 타입 A의 값이 필요한 모든 장소에 어떤 타입 B의 값을 넣어도 아무 문제가 없다면 타입 B는 타입 A의 하위 타입이다.
 * 예를 들어 Int는 Number의 하위 타입이지만 String의 하위 타입은 아니다. 이 정의는 모든 타입이 자신의 하위 타입이라는 뜻이기도 하다.
 * 반대로 상위 타입(supertype)은 하위 타입의 반대다.
 */

fun test(i: Int) {
    val n: Number = i
    fun f(s: String) { /* ... */
    }
//    f(i) // Error
}
/* 위의 예제처럼 어떤 함수의 파라미터 타입이 하위 타입인 경우에만 함수 호출이 허용된다. */

/** 간단한 경우 하위 타입은 하위 클래스(subclass)와 근본적으로 같다.
 * 예를 들어 Int 클래스는 Number클래스의 하위 클래스이므로 Int는 Number의 하위 타입이다.
 * String은 CharSequence의 하위 타입인 것처럼 어떤 인터페이스를 구현하는 클래스의 타입은 그 인터페이스의 하위 타입이다.
 * 널이 될 수 없는 타입은 널이 될 수 있는 타입의 하위 타입이다.
 * */


/** 자 그러면 List<String>타입의 값을 List<Any>를 파라미터로 받는 함수에 전달해도 괜찮은가?
 * 이는 List<String>은 List<Any>의 하위 타입인가? 로 봐도 무방하다. (하위 타입이라고 볼 수 있지만, MutableList의 경우 어떠한 관계도 없다.)
 *
 * 제네릭 타입을 인스턴스화할 때 타입 인자로 서로 다른 타입이 들어가면 인스턴스 타입 사이의
 * 하위 타입 관계가 성립하지 않으면 그 제네릭 타입을 무공변(invariant)이라고 말한다. (자바는 모든 클래스가 무공변이다.)
 *
 * 참고로 코틀린의 List 인터페이스는 읽기 전용 컬렉션을 표현한다. A가 B의 하위 타입이면 List<A>는 List<B>의 하위 타입이다.
 * 그런 클래스나 인터페이스를 공변적(covaiant)이라 말한다.
 */


/** 409p A가 B의 하위 타입일 때 Producer<A>가 Producer<B>의 하위 타입이면 Producer는 공변적이다.
[이를 하위 타입 관계가 유지된다고 말한다.]
코틀린에서 제네릭 클래스가 타입 파라미터에 대해 공변적임을 표시하려면 타입 파라미터 이름 앞에 out을 넣어야 한다. */
interface Producer<out T> { // 타입 T에 대해 공변적이라는 의미.
    fun produce(): T
}


/** 클래스의 타입 파라미터를 공변적으로 만들면 함수 정의에 사용한 파라미터 타입과 타입 인자의 타입이 정확히 일치하지
 * 않더라도 그 클래스의 인스턴스를 함수 인자나 반환 값으로 사용할 수 있다.
 */
open class Animal {
    fun feed() {}
}


class Herd<out T : Animal> { // 무공변성
    val size: Int get() = 10
    val animals: List<T> = ArrayList(10)
    operator fun get(i: Int): T = animals[i]
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) {
        animals[i].feed()
    }
}

class Cat : Animal() {
    fun cleanLitter() {}
}

fun takeCareOfCats(cats: Herd<Cat>) {
    for (i in 0 until cats.size) {
        cats[i].cleanLitter()
//        feedAll(cats) // error남.
        feedAll(cats) // Herd<out T: Animal> 이렇게하면 됨.
    }
}

/** feedAll()함수에 cats를 넘기면 type mismatch 오류가 난다. Herd 클래스의 T 타입 파라미터에 대해 아무 변성도
 * 지정하지 않았기 때문이다. 명시적으로 타입 캐스팅을 사용하면 해결 가능 하지만
 * 코드가 장황해지고 실수하기 쉽다. 또한 타입 불일치를 해결하기 위해 강제 캐스팅을 하는 것은 올바르지 못하다.
 */

/** 클래스 멤버를 선언할 때 타입 파라미터를 사용할 수 있는 지점은 모두 인(in)과 아웃 위치로 나뉜다.
 * T라는 타입 파라미터를 선언하고 T를 사용하는 함수가 멤버로 있는 클래스를 생각해보면 T가 함수의 반환 타입에 쓰인다면
 * T는 아웃 위치에 있다. 그 함수는 T타입의 값을 생산(produce)한다. T가 함수의 파라미터 타입에 쓰인다면 T는 인 위치에 있다.
 * 그런 함수는 T 타입의 값을 소비(consume)한다.
 */
interface Transformer<T> {
    fun transform(t: T): T    // t: T는 in위치, :T는 out위치
}
/* 함수 파라미터 타입은 인 위치, 함수 반환 타입은 아웃 위치에 있다. */
/** 클래스 타입 파라미터 T 앞에 out 키워드를 붙이면 클래스 안에서 T를 사용하는 메서드가
 * 아웃 위치에서만 T를 사용하게 허용하고 인 위치에서는 T를 사용하지 못하게 막는다. out 키워드는 T의 사용법을
 * 제한하며 T로 인해 생기는 하위 타입 관계의 타입 안정성을 보장한다.
 */
/* 즉, out 키워드를 타입 파라미터에 붙이면 해당 타입은 반환 타입으로만 사용 할 수 있고,
자동으로 공변성을 띄게 되는것 같다. */
/** 이 위치(함수의 반환 타입)는 아웃 위치다. 따라서 이 클래스를 공변적으로 선언해도 안전하다. Cat이 Animal의 하위 타입이기
 * 때문에 Herd<Animal>의 get을 호출하는 모든 코드는 get이 Cat을 반환해도 아무 문제없이 작동한다.
 */


/** 정리하면!! 타입 파라미터 T에 붙은 out 키워드는 두 가지를 함께 의미한다. */
// - 공변성: 하위 타입 관계가 유지된다.(Producer<Cat>은 Producer<Animal>의 하위 타입)
// - 사용 제한: T를 아웃 위치에서만 사용할 수 있다.

/** 이제 List를 보자 List는 읽기 전용이고, 이 안에는 T타입의 원소를 반환하는 get()메소드는 있지만 리스트에 T타입의 값을
 * 추가하거나 리스트에 있는 기존 값을 변경하는 메서드는 없다. 따라서 List는 T에 대해 공변적이다.
 */
//interface List<out T> : Collection<T> {
//    operator fun get(index: Int): T // 읽기 전용 메소드로 T를 반환하는 메서드만 정의한다. 항상 아웃 위치임.
//}
/** 타입 파라미터를 반환 타입에만 쓸 수 있는것은 아니다. 다른 타입의 인자로도 사용할 수 있다. */
//interface List<out T>: Collection<T> {
//    fun subList(fromIndex: Int, toIndex: Int) : List<T> // 여기서도 T는 아웃 위치에 있음.
//}

/** 여기서 MutableList<T>를 타입 파라미터 T에 대해 공변적인 클래스를 선언할 수는 없다는 점에 유의해야 한다. */
//interface MutableList<out T> : List<T>, MutableCollection<T> {
//    override fun add(element: T): Boolean { // 여기서 T는 in위치에 있음.
//
//    }
//}

/** 그렇다면 변성은 왜 사용할까? 바로 위험할 여지가 있는 메서드를 호출할 수 없게 만듦으로써 제네릭 타입의 인스턴스 역할을
 * 하는 클래스 인스턴스를 잘못 사용하는 일이 없게 방지하는 역할을 한다.
 * 생성자는?? 메서드가 아니므로 위험할 여지가 없다.
 * 하지만 val이나 var키워드를 생성자 파라미터에 적는다면 이는 게터, 세터를 정의하는 것과 같다.
 * 따라서 읽기 전용 프로퍼티는 아웃 위치, 변경 가능
 * 프로퍼티는 아웃과 인 위치 모두에 해당한다.
 */

// class Herdd<T: Animal>(var leadAnimal: T, vararg animals: T) { ... }
/* 여기서 T 타입은 leadAnimal 프로퍼티가 인 위치에 있기 떄문에 T를 out 으로 표시할 수 없다. */
/** 이러한 규칙은 오직 외부에서 볼 수 있는 public, protected, internal 클래스 API에만 적용할 수 있다.
 * 비공개(private) 메소드의 파라미터는 인도 아니고 아웃도 아닌 위치다. 변성 규칙은 클래스 외부의 사용자가 클래스를 잘못
 * 사용하는 일을 막기 위한 것이므로 클래스 내부 구현에는 적용되지 않는다.
 */


/** 414p 반공변성: 뒤집힌 하위 관계
 * 반공변성(contravariance)은 공변성의 반대다. 예를 들어 Comparator 인터페이스를 살펴보면 이 인터페이스는 compare라는 메소드가 있다.
 *
 */
//interface Comparator<in T> {
//    fun compare(e1: T, e2: T): Int { ... } // <- T를 in 위치에 사용
//}

//fun main() {
//    val anyComparator = Comparator<Any> { e1, e2 -> e1.hashCode() - e2.hashCode() }
//    val strings = listOf("c", "a", "b")
//    println(strings.sortedWith(anyComparator))
//}

/** sortedWith 함수는 Comparator<String>(문자열을 비교하는 Comparator)을 요구하므로, String보다 더 일반적인 타입을
 * 비교할 수 있는 Comparator를 sortedWith에 넘기는 것은 안전하다. 어떤 타입의 객체를 Comparator로 비교해야 한다면 그 타입이나
 * 그 타입의 조상 터입을 비교할 수 있는 Comparator를 사용할 수 있다. 이는 Comparator<Any>가 Comparator<String>의 하위 타입이라는 뜻이다.
 * 그런데!!!! 여기서 Any는 String의 상윔 타입이다. 따라서 서로 다른 타입 인자에 대해 Comparator의 하위 타입 관계는 타입 인자의
 * 하위 타입 관계와는 정반대 방향이다.
 * Consumer<T>를 예로 들어 보면, 타입 B가 타입 A의 하위 타입인 경우 Consumer<A>가 Comsumer<B>의 하위 타입인 관계가 성립하면
 * 제네릭 클래스 Consumer<T>는 타입 인자 T에 대한 반공변이다.
 *
 */

/** 변성에 대해 요약해 보면... */
/*
  공변성
  Producer<T>
  타입 인자의 하위 타입 관계가 제네릭 타입에서도 유지된다.
  Producer<Cat>은 Producer<Animal>의 하위 타입이다.
  T를 아웃 위치에서만 사용할 수 있다.
 */

/*
  반공변성
  Consumer<T>
  타입 인자의 하위 타입 관계가 제네릭 타입에서 뒤집힌다.
  Consumer<Animal>은 Consumer<Cat>의 하위 타입이다.
  T를 인 위치에서만 사용할 수 있다.
 */

/*
   무공변성
   MutableList<T>
   하위 타입 관계가 성립하지 않는다.
   T를 아무 위치에서나 사용할 수 있다.
 */


/** 클래스나 인터페이스가 어떤 타입 파라미터에 대해서는 공변적이면서 다른 타입 파라미터에 대해서는
 * 반공변적일 수도 있다.
 */
interface Function1<in P, out R> {
    operator fun invoke(p: P): R
}

/** 코틀린 표기에서 (P) -> R은 Function1<P, R>을 더 알아보기 쉽게 적은 것일 뿐이다.
 * 여기서 P(함수 파라미터의 타입)는 오직 인 위치, R(함수 반환 타입)은 오직 아웃 위치에 사용된다는 사실과
 * 그에 따라 P와 R에 각각 in과 out 표시가 붙어 있음을 볼 수 있다. 이는 첫 번째 타입 인자의 하위 타입 관계와는
 * 반대지만 두 번째 타입 인자의 하위 타입 관계와는 같음을 뜻한다
 *
 * Animal은 Cat의 상위 타입이고, Int는 Number의 하위 타입이므로 이는 옳은 식임.
 * */
//fun enumerateCats(f: (Cat) -> Number) {}
//fun Animal.getIndex(): Int = 0
//fun main() {
//    enumerateCats(Animal::getIndex)
//}


/** 418p 사용 지점 변성: 타입이 언급되는 지점에서 변성 지정
 * 클래스를 선언하면서 변성을 지정하면 그 클래스를 사용하는 모든 장소에 변성 지정자가 영향을 끼치므로 편리하다.
 * 이런 방식을 선언 지점 변성(declaration site variance)이라 부른다. 자바의 와일드카드 타입(? extends나 ? super)
 * 과는 다름. 자바에서는 타입 파라미터가 있는 타입을 사용할 때마다 해당 타입 파라미터를 하위 타입이나 상위 타입 중
 * 어떤 타입으로 대치할 수 있는지 명시해야 한다. 이런 방식을 사용 지점 변성(use-site variance)이라 부른다.
 *
 * 물론 코틀린도 사용 지점 변성을 지원한다. 클래스 안에서 어떤 타입 파라미터가 공변적이거나 반공변적인지 선언할 수 없는 경우에도
 * 특정 타입 파라미터가 나타나는 지점에서 변성을 정할 수 있다.
 * */

/** 무공변 파라미터 타입을 사용하는 데이터 복사 함수 */
fun <T> copyData(
    source: MutableList<T>,
    destination: MutableList<T>) {
    for (item in source) {
        destination.add(item)
    }
}
/* 이 함수는 컬렉션의 원소를 다른 컬렉션으로 복사한다. 두 컬렉션 모두 무공변 타입이지만 원본 컬렉션에서는 읽기만 하고
대상 컬렉션에서는 쓰기만 한다. 이 경우 두 컬렉션의 원소 타입이 정확하게 일치할 필요가 없다.
예를 들어 문자열이 원소인 컬렉션에서 객체의 컬렉션으로 원소를 복사해도 아무 문제가 없다.
 */

/** 타입 파라미터가 둘인 데이터 복사 함수 */
fun <T: R, R> copyData2(
    source: MutableList<T>, // source 원소 타입은 destination 원소 타입의 하위 타입이어야 함.
    destination: MutableList<R>
) {
    for (item in source) {
        destination.add(item)
    }
}
/** Int는 Any의 하위 타입이다. 하지만 코틀리넹서는 더 우아하게 표현할 수 있는 방법이 있다.
 * 함수 구현이 아웃 위치에 있는 타입파라미터를 사용하는 메서드만 호출한다면 그런 정보를 바탕으로
 * 함수 정의 시 타입 파라미터에 변성 변경자를 추가할 수 있다.
 */


/** 아웃-프로젝션 타입 파라미터를 사용하는 데이터 복사 함수 */
fun <T> copyData3(
    source: MutableList<out T>,
    destination: MutableList<T>
) {
    for (item in source) {
        destination.add(item)
    }
}

//fun main() {
//    val intList = mutableListOf(1,2,3)
//    val anyList = mutableListOf<Any>()
//    copyData3(intList, anyList)
//}

/** 타입 선언에서 타입 파라미터를 사용하는 위치라면 어디에나 변성 변경자를 붙일 수 있다. 따라서 파라미터 타입,
 * 로컬 변수 타입, 함수 반환 타입 등에 타입 파라미터가 쓰이는 경우 in이나 out 변경자를 붙일 수 있다.
 * 이....때!!! 타입 프로젝션(Type Projection)이 일어난다. 즉, source를 일반적인 MutableList가 아니라 MutableList를
 * 프로젝션을 한(제약을 가한)타입으로 만든다. 이 경우 copyData3 함수는 MutableList의 메소드 중에서
 * 반환 타입으로 타입 파라미터 T를 사용하는 메소드만 호출할 수 있다. (더 정확하게는 타입 파라미터 T를 아웃 위치에서 사용하는
 * 메소드만 호출할 수 있다.) 컴파일러는 타입 파라미터 T를 함수 인자 타입(더 정확하게는 인 위치에 있는 타입)로 사용하지 못하게 막는다.
 */

//fun main() {
//    val list: MutableList<out Number> = mutableListOf(1,2,3)
////    list.add(42) // 위치가 out 위치이므로 add()메소드를 호출 할 수 없음.
//}

/** 여기서 가령 List<out T>처럼 out 변경자가 지정된 타입 파라미터를 out 프로젝션 하는 것은 의미 없다.
 * List의 정의는 이미 class List<out T>이므로 List<out T>는 그냥 List<T>와 같다.
 * 비슷한 방식으로 in을 붙이면 그 파라미터를 더 상위 타입으로 대치할 수 있다.
 */
fun <T> copyData4(source: MutableList<T>,
destination: MutableList<in T>) { // 원본 리스트 타입의 상위 타입을 대상 리스트의 원소 타입을 허용
    for (item in source) {
        destination.add(item)
    }
}
/* kotlin : MutableList<out T> == java : MutableList<? extends T>
   kotlin : MutableList<in T> == java : MutableList<? super T>
 */

/** 스타 프로젝션 : 타입 인자 대신 * 사용
 * 가령 원소 타입이 알려지지 않은 리스트는 List<*>라는 구문으로 표현할 수 있다.
 * 스타 프로젝션의 의미.
 * 1. MutableList<*>는 MutableList<Any?>와 같지 않다.
 * MutableList<Any?>는 모든 타입의 원소를 담을 수 있다는 사실을 알 수 있는 리스트다. 반면
 * MutableList<*>는 어떤 정해진 구체적인 타입의 원소만을 담는 리스트지만 그 원소의 타입을 정확히 모른다는
 * 사실을 표현한다. 하지만 어떠한 구체적인 원소를 저장하기 위해 만들어진 것이라는 뜻이다.
 * 그렇다고 아무 원소나 다 담는다는 뜻은 아니다. 그 리스트에 담는 값의 타입에 따라서는 리스트를 만들어서
 * 넘겨준 쪽이 바라는 조건을 깰 수도 있기 때문이다. 하지만 MutableList<*> 타입의 리스트에서
 * 원소를 얻을 수는 있다. 그런 경우 진짜 원소 타입은 알 수 없지만 어쨋든 그 원소 타입이 Any?의 하위 타입이라는 사실은
 * 분명하다. Any?는 코틀린에서 모든 타입의 상위 타입이기 때문이다.
 */


/** 423p 참조 컴파일러는 MutableList<*>에 원소를 추가하려고 하면 Out-projected type ... 이라는 error를 발생시킨다.
 * 이유는 어떤 리스트의 원소 타입을 모르더라도 그 리스트에서 안전하게 Any? 탕립의 원소를 꺼내올 수는 있지만
 * 타입을 모르는 리스트에 원소를 마음대로 넣을 수는 없다. */


/** 타입 파라미터를 시그니처에서 전혀 언급하지 않거나 데이터를 읽기는 하지만 그 타입에는 관심이 없는 경우와 같이 타입
 * 인자 정보가 중요하지 않을 때도 스타 프로젝션 구문을 사용할 수 있다.
 */
fun printFirst(list: List<*>) {
    if (list.isNotEmpty()) println(list.first())
}
// 비슷한 코드로... 타입 파라미터를 사용한 제네릭 함수
fun <T> printFirst2(list: List<T>) {
    if (list.isNotEmpty()) println(list.first())
}
/* 스타 프로젝션을 쓰는 쪽이 더 간결하지만 제네릭 타입 파라미터가 어떤 타입인지 굳이 알 필요가 없을 때만 스타 프로젝션을
사용 할 수 있다. 스타 프로젝션을 사용할 때는 값을 만들어내는 메소드만 호출할 수 있고 그 값의 타입에는
신경을 쓰지 말아야 한다.
 */


/* 입력 검증을 위한 인터페이스 : 스타 프로젝션이 빠지기 쉬운 함정을 보자.
FieldValidateor라는 인터페이스를 정의했다고 가정하고 이는 인 위치에만 쓰이는 타입 파라미터가 있음.
실제로 String 타입의 필드를 검증하기 위해 Any 타입을 검증하는 FieldValidator를 사용할 수 있다.
 */
interface FieldValidator<in T> { // T에 대해 반공변인 인터페이스 선언
    fun validate(input: T): Boolean // T를 in위치에만 사용
}
object DefaultStringValidator: FieldValidator<String> {
    override fun validate(input: String): Boolean = input.isNotEmpty()
}
object DefaultIntValidator: FieldValidator<Int> {
    override fun validate(input: Int): Boolean = input >= 0
}

/** 이제 모든 검증기를 컨테이너에 넣고, 입력 필드의 타입에 따라 적절한 검증기를 꺼내서 사용하는 경우를 생각해보면,
 * 맵에 검증기를 담아서 KClass를 키로 하고 FiledValidator<*>를 값으로 하는 맵을 선언한다.
 */

//fun main() {
//    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
//    validators[String::class] = DefaultStringValidator
//    validators[Int::class] = DefaultIntValidator
//}
/** 이렇게 작성하면 String 타입의 필드를 FieldValidator<*>타입의 검증기로 검증할 수 없다.
 * 컴파일러는 FieldValidator<*>가 어떤 타입을 검증하는 검증기인지 모르기 때문에 String을 검증하기 위해 그 검증기를
 * 사용하면 안전하지 않다고 판단한다.
 */

//fun main() {
//    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
////    validators[String::class]!!.validate("") // OutProject만 된다는 Error가 발생함.
//}
/** MutableList<*> 타입의 리스트에 원소를 넣으려고 했을 때 이 오류를 본 적이 있다. 여기서 이 오류는 알 수 없는
 * 타입의 검증기에 구체적인 타입의 값을 넘기면 안전하지 못하다는 뜻이다. 이런 경우 검증기가 원하는 타입으로 캐스팅하면
 * 문제를 고칠 수 있다. 하지만 그런 타입 캐스팅은 안전하지 못하고 권장할 수 없다.
 */

/** 426p 검증기를 가져오면서 명시적 타입 캐스팅 사용 */
//fun main() {
//    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
//    validators[String::class] = DefaultStringValidator
//
//    val stringValidator = validators[String::class] as FieldValidator<String>
//    println(stringValidator.validate(""))
//}
/** 컴파일러는 타입 캐스팅이 안전하지 못하다고 경고한다. 또한 이 코드를 실행하면
 * 타입 캐스팅 부분에서 실패하지 않고 값을 검증하는 메소드 안에서 실패한다는 사실을 유의해야 한다.
 * 실행 시점에 제네릭 타입 정보는 사라지므로 타입 캐스팅은 문제가 없고 검증 메서드 안에서 값의 메서드나
 * 프로퍼티를 사용할 때 문제가 생긴다.
 */


/** 검증기를 잘못 가져온 경우 */
//fun main() {
//    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
//    validators[Int::class] = DefaultIntValidator
//
//    val stringValidator = validators[Int::class] as FieldValidator<String> // 경고표시를 하지만 컴파일은 됨.
//    println(stringValidator.validate("")) // 이 검증기를 사용해야 오류 남(실행 시간에)
//}
/** 올바른 타입의 검증기를 가져와서 사용하는 몫은 이제 개발자 몫이 되었다.
 * 이런 해법은 타입 안전성을 보장할 수도 없고 실수를 하기도 쉽다. 따라서 한 장소에 여러 다른 타입의 검증기를
 * 보관할 좋은 방법이 있다.
 * 해당 코드는 Validators객체가 맵에 대한 접근을 통제해서 맵에 잘못된 값이 들어가지 못하게 막을 수 있다.
 */
/** 427p 검증기 컬렉션에 대한 캡슐화 */
object Validators {
    private val validators = // 앞 예제와 유사해 보이지만 해당 검증기에 접근 불가
        mutableMapOf<KClass<*>, FieldValidator<*>>()

    fun<T: Any> registerValidator(kClass: KClass<T>, fieldValidator: FieldValidator<T>) {
        validators[kClass] = fieldValidator
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T: Any> get(kCLass: KClass<T>): FieldValidator<T> =
        validators[kCLass] as? FieldValidator<T>
            ?: throw IllegalArgumentException(
                "No validator for ${kCLass.simpleName}"
            )
}

fun main() {
    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)

    println(Validators[String::class].validate("Kotlin"))
    println(Validators[Int::class].validate(2))
}
/** 이제 타입 안정성을 보장하는 API를 만들었다. 안전하지 못한 모든 로직은 클래스 내부에 감춰졌다.
 * 그리고 안전하지 못한 부분을 감춤으로써 이제는 외부에서 그 부분을 잘못 사용하지 않음을 보장할 수 있다.
 *  Validators 객체에 있는 제네릭 메서드에서 검증기(FieldValidator<T>와 클래스 KClass<T>의 타입 인자가 같기 때문에
 *  컴파일러가 타입이 일치하지 않는 클래스와 검증기를 등록하지 못하게 막는다.
 */


