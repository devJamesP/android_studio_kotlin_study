package chapter7

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import java.lang.reflect.Type
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/** 앞에 operator를 꼭 붙여야 한다.
 * 연산자를 오버로딩하는 함수 앞에는 꼭 operator이 있어야 한다.
 * euqals예외
 */

data class Point(val x: Int, val y: Int) {
    /** equals() 컨벤션 메소드*/
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point) return false
        return this.x == other.x && this.y == other.y
    }

    override fun hashCode(): Int {
        return this.x.hashCode() + this.y.hashCode() + 123
    }
}

operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
}

operator fun Point.minus(other: Point): Point {
    return Point(x - other.x, y - other.y)
}

operator fun Point.times(scale: Double): Point {
    return Point((x * scale).toInt(), (y * scale).toInt())
}

operator fun Point.div(other: Point): Point {
    if (other.x == 0 || other.y == 0) throw IllegalArgumentException("other point should not 0")
    return Point(x * other.x, y * other.y)
}

operator fun Point.rem(other: Point): Point {
    if (other.x == 0 || other.y == 0) throw IllegalArgumentException("other point should not 0")
    return Point(x * other.x, y * other.y)
}

operator fun Char.times(count: Int): String {
    return this.toString().repeat(count)
}

//operator fun<T> MutableCollection<T>.plusAssign(element: T) {
//    this.add(element)
//}

operator fun BigDecimal.inc() = this + BigDecimal.ONE
operator fun BigDecimal.dec() = this - BigDecimal.ONE


/** 여기서부터는 비교(부등호, 동등 비교는 equals) 연산자 관련 관례 */
class Person(private val firstName: String, private val lastName: String) : Comparable<Person> {
    override fun compareTo(other: Person): Int {
        return compareValuesBy(this, other, Person::firstName, Person::lastName)
    }
}

/** 인덱스 연산자 convention*/
/** [ ]에 대한 convension : get(), set() */
operator fun Point.get(index: Int): Int {
    return when (index) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

data class TestMap(val key: String, val value: Int)

operator fun TestMap.get(key: String): Int {
    return this.value
}

data class MutablePoint(var x: Int, var y: Int)

operator fun MutablePoint.set(index: Int, value: Int) {
    when (index) {
        0 -> x = value
        1 -> y = value
        else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}


/** 열린 범위 관례 : T.contains(T) == in , 닫힌 범위 관례 : T.rangeTo(T) == .. */
data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
    return p.x in upperLeft.x until lowerRight.x &&
            p.y in upperLeft.y until lowerRight.y
}


/** iterator 관례 :: for루프에서의 in연산자의 관례는 앞서 in과는 다르다.
 * for (i in list) { ... } 문장은 list.iterator()를 호출해서 이터레이터를 얻은 다음,
 * 자바와 마찬가지로 그 이터레이터에 대해 hasNext와 next 호출을 반복하는 식으로 변환된다.
 *
 * 하지만 코틀린에서는 이 또한 convension이므로 iterator 메소드를 확장 함수로 정의할 수 있다.
 * 이런 성질로 인해 일반 자바 문자열에 대한 for 루프가 가능하다. 코틀린 표준 라이브러리는
 * String의 상위 클래스인 CharSequence에 대한 iterator확장 함수를 제공한다.
 */
operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
    object : Iterator<LocalDate> {
        var current = start
        override fun hasNext(): Boolean {
            return current <= endInclusive
        }

        override fun next(): LocalDate {
            return current.apply {
                current = plusDays(1L)
            }
        }
    }


/** 구조 분해 선언과 component 함수
 * 구조 분해 선언 시 컴파일러가 자동으로 componentN함수를 만들어 준다.
 */
// 이런식으로 자동으로 주 생성자에 선언한 순서대로 매칭됨.
data class NameComponents(val name: String, val extension: String)

fun splitFilename(fullName: String): NameComponents {
    val (name, ext) = fullName.split('.', limit = 2)
    return NameComponents(name, ext)
}


fun printEntries(map: Map<String, String>) {
    for ((key, value) in map) {
        println("$key -> $value")
    }

    for (entry in map) {
        val key = entry.component1()
        val value = entry.component2()
        println("$key -> $value")
    }
}

/** delegated property(위임 프로퍼티) 디자인 패턴 중에 하나로 객체가 직접 작업을 수행하지 않고
 * 다른 도우미 객체가 그 작업을 처리하게 맡기는 패턴.
 */


open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}

/* 자바에서는 보통 리스너를 통해 객체의 값이 바뀌었을때 이를 알리기 위해
PropertyChangeSupport와 PropertyChangeEvent 클래스를 사용한다.
밑에 코드처럼 작성하게 되면 값이 바뀌었을 때 해당 코드가 변경되었다는 이벤트
콜백 함수가 호출되는데, 이때 세터 코드가 여러번 중복된다.
 */
class Person2(
    val name: String, age: Int, salary: Int
) : PropertyChangeAware() {
    var age: Int = age
        set(newValue) {
            val oldValue = field
            field = newValue
            changeSupport.firePropertyChange("age", oldValue, field)
        }

    var salary: Int = salary
        set(newValue) {
            val oldValue = field
            field = newValue
            changeSupport.firePropertyChange("salary", oldValue, field)
        }
}

/* 위 코드를 개선한 코드이다. 도우미 클래스를 통해 프로퍼티 변경을 통지한다.
* 337 페이지 참고 */
class ObservableProperty(
    private var propValue: Int,
    private val changeSupport: PropertyChangeSupport
) {
    operator fun getValue(p: Person3, prop: KProperty<*>): Int = propValue
    operator fun setValue(p: Person3, prop: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name, oldValue, propValue)
    }
}


/* 다음과 같이 작성하면 ObservableProperty 클래스는 실제 위임이 작동하는 방식과
유사하게 동작한다. -> 이를 다시 관례에 맞게 고쳤음.
 */
class Person3(name: String, age: Int, salary: Int) : PropertyChangeAware() {
    var age: Int by ObservableProperty(age, changeSupport)
    var salary: Int by ObservableProperty(salary, changeSupport)

}

/** Delegates의 observable을 사용해 프로퍼티 변경 통지 구현
 * 아주 elegangse !! */
class Person4(
    val name: String, age: Int, salary: Int
): PropertyChangeAware() {
    private val observar = {
        prop: KProperty<*>, oldValue: Int, newValue: Int ->
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
    val age: Int by Delegates.observable(age, observar)
    val salary: Int by  Delegates.observable(salary, observar)
}


//class C {
//    private val <delegate> = MyDelegate()
//    var prop: Type
//    get() = <delegate>.getValue(this, <property>)
//    set(value: Type) = <delegate>.setValue(this, <property>, value)
//}
//val c = C()


class Person5 {
    // 추가 정보
    private val _attributes = hashMapOf<String, String>()

    fun setAttribute(attrName: String, value: String) {
        _attributes[attrName] = value
    }

    // 필수 정보
    val name: String
    get() = _attributes["name"]!! // <- 수동으로 맵에서 정보를 꺼냄
}


class Person6 {
    private val _attributes = hashMapOf<String, String>()

    fun setAttribute(attrName: String, value: String) {
        _attributes[attrName] = value
    }

    val name: String by _attributes // <- 위임 프로퍼티 맵을 사용한다.
    val company: String by _attributes // 사실상 _attributes.getValue(company, prop)이 호출된거임.


    /** 이런 코드의 작동 이유는 표준 라이브러리가 Map과 MutableMap 인터페이스에 대해
     * getValue와 setValue 확장 함수를 제공하기 때문이다.
     * getValue에서 맵에 프로퍼티 값을 저장할 때는 자동으로 프로퍼티 이름을 키로 활용한다.
     * p라는 Person6객체를 생성하여
     * p.name은 getValue(p, prop)이라는 호출이 대신하고, 이는 다시
     * _attributes.getValue(p, prop)을 통해 구현한다.
     */
}


fun main() {
    val p = Person6()
    val data = mapOf("name" to "Dmitry", "company" to "JetBrains")
    for((attrName, value) in data) {
        p.setAttribute(attrName, value)
    }
    println(p.company)
}







