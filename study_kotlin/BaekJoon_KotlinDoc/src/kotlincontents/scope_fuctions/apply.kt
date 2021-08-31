package kotlincontents.scope_fuctions

/**
 * apply함수는 객체의 블록 내 코드를 실행하고, 객체 자신을 반환합니다.
 * 람다식 내부 인자로 호출자 자기 자신을 넘기기 때문에 this로 접근할 수 있으며(this는 생략 가능합니다.)
 * 이럴 때 사용합니다 :: 보통 객체를 초기화 할 때 사용합니다.
 */

fun main() {
    val jake = Person3()

    val stringDescription = jake.apply {
        this.name = "James"
        this.age = 300
        this.about = "Android Developer"
    }

    println(stringDescription)
}

data class Person3(var name: String = "", var age: Int = -1, var about: String = "")     // 1