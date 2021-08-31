package kotlincontents.scope_fuctions

/**
 * also는 apply와 유사하게 동작합니다. 호출자(객체)를 람다식의 인자로 넘깁니다. 블록(람다식) 내부에서
 * 객체를 it으로 참조합니다.(명시적으로 넘긴다는 의미겠죠?) 따라서 객체를 it으로 쉽게 넘길 수 있습니다.
 * 이럴 때 사용합니다 :: 객체의 속성이나, 상태를 변경하지 않고 사용할 때, 그래서 대게 데이터의 유효성을 검사하거나
 * 디버깅, 로그 등을 확인하는 목적으로 사용합니다.
 * ex) 안드로이드에서 다른 액티비티를 시작 할 때 startActivity(Intent)를 호출하고 Intent 객체를 넘기는데
 * 이때 사용할 수도 있습니다.
 * Intent(...).also {
 *     startActivity(it)
 * } 이런식으로.... */

fun main() {
    val james = Person3("james", 300, "Android Developer")
        .also {
            writeCreationLog(it)
        }
}

fun writeCreationLog(person : Person3) {
    println("Log :: name: ${person.name}, age: ${person.age}, about: ${person.about}")
}