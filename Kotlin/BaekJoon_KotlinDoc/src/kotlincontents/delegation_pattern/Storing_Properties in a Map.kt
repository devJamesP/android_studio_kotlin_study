package kotlincontents.delegation_pattern

/**
 프로퍼티 델리게이션은 map에 저장될 수 있습니다.
 이것은 JSON파싱이나 동적 작업과 같은 작업에 편리합니다.
 */

class User(val map: Map<String, Any?>) {
    val name: String by map // 1
    val age: Int by map // 1
}

fun main() {
    val user = User(mapOf(
        "name" to "John Doe",
        "age" to 25
    ))

    println("name = ${user.name}, age = ${user.age}")
}

/* 델리게이트는 문자열 키(name)로 맵에서 값을 가져옵니다. */