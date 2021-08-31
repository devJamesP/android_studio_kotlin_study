package kotlincontents.productivity_boosters

/**
 * 다른 언어들(Java, C++, etc)과 마찬가지로 Kotlin도 생성자나 메소드를 인자로 넘길 수 있습니다.
 * 코틀린은 잘못된 호출 또는 순서의 실수를 줄이기 위해 named arguments를 지원합니다.
 * 이러한 실수들은 컴파일러가 찾지 못하기 때문에 실수를 찾기가 어렵습니다.
 */

fun main() {
    println(format("mario", "example.com"))                         // 1
    println(format("domain.com", "username"))                       // 2
    println(format(userName = "foo", domain = "bar.com"))           // 3
    println(format(domain = "frog.com", userName = "pepe"))         // 4
}

fun format(userName: String, domain: String): String =
    "$userName@$domain"