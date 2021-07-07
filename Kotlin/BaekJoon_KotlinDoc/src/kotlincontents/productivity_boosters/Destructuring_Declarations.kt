package kotlincontents.productivity_boosters

/**
 * Destructuring declaration 구문은 매우 편리합니다. 멤버에 접근을 위한
 * 인스턴스가 필요할 때 특히 유용합니다. 특정 이름없이 인스턴스를 정의 할 수 있으므로 코드 몇 줄을
 * 절약 할 수 있습니다.
 */

fun main() {
    //1
    val (x, y, z) = arrayOf(5, 10, 15)

    val map = mapOf("Alice" to 21, "Bob" to 25)
    for((name, age) in map) {
        println("$name is $age years old.")
    }

    //2
    val user = getUser()
    val (username, email) = user
    println(username == user.username)

    val (_, emailAddress) = getUser()

    //3
    val (num, name) = Pair(1, "one")
    println("num = $num, name = $name")

}


//2
data class User(val username: String, val email: String)
fun getUser() = User("Mary", "mary@somewhere.com")


//3
class Pair<K, V>(val first: K, val second: V) {     // 1
    operator fun component1() : K {
        return first
    }
    operator fun component2() : V {
        return second
    }
}