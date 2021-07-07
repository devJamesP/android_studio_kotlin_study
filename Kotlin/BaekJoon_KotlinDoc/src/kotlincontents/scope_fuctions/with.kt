package kotlincontents.scope_fuctions

/**
 * with은 확장함수가 아닙니다. with함수의 인자로 받은 객체의 멤버에 접근할 수 있습니다.
 * 어떻게 보면 run과 유사합니다.
 * 이럴때 사용합니다 :: with은 null이 아닌 객체를 인자로 받으며, return값이 필요하지 않을 때 사용합니다.
 * 그래서 객체의 여러 멤버 메소드를 그룹화할때 사용합니다.
 */
fun main() {
    val configuration = Configuration("James", "1234")

    println("${configuration.host}:${configuration.port}") // 이거 대신에

    // 이렇게 간편하게~
    with(configuration) {
        println("$host:$port")
    }
}

data class Configuration(
    val host: String,
    val port: String
)