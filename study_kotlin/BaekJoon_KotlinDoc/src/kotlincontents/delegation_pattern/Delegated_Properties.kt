package kotlincontents.delegation_pattern

import kotlin.reflect.KProperty


/**
 * Kotlin은 특정 객체에 메서드를 get하거나 set할 수 있는
 * 프로퍼티의 호출을 위임할 수 있는 델리게이트 프로퍼티 메커니즘을 제공합니다.
 * 델리게이트 객체는 getValue()메소드를 반드시 있어야 하고, 해당 프로퍼티가 수정 가능하다면 setValue()도 필요합니다.
 */

class Example {
    var p: String by Delegate()                                               // 1
    var p2: String by Delegate2()

    override fun toString() = "Example Class"
}

class Delegate() {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {        // 2
        return "$thisRef, thank you for delegating '${prop.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) { // 2
        println("$value has been assigned to ${prop.name} in $thisRef")
    }
}

class Delegate2() {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
        return "$thisRef is delegate poperty. property name is ${prop.name}"
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Any?) {
        println("value : $value, has been assigned to ${prop.name} in $thisRef")
    }
}
fun main() {
    val e = Example()
    println(e.p)
    e.p = "NEW"

    val e2 = Example()
    println(e2.p2)
    e2.p2 = "TEST"
}

/*
1. String타입의 p 프로퍼티를 Delegate 클래스 인스턴스에 위임합니다. 델리게이트 객체는
by 키워드 뒤에 정의됩니다.
2. 위임 메소드입니다. 해당 메소드들은 예제에 나오는것과 유사하게 되어있습니다.
만약 변경 불가한 델리게이트 프로퍼티라면 getValue만 필요합니다.
 */