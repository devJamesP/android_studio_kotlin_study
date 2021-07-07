package kotlincontents.specialclasses

data class User(val name : String, val id : Int) {
    // User타입이고 id가 같으면 같은 User로 보도록 함.
    override fun equals(other: Any?): Boolean {
        return other is User && other.id == id
    }
}

fun main() {
    val user = User("Alex", 1)
    val user2 = User("Amily", 1)
    println(user)


    val secondUser = User("Alex", 1)
    val thirdUser = User("Max", 2)

    println("user == secondUser: ${user == secondUser}")
    println("user == thirdUser: ${user == thirdUser}")
    println("user == user2: ${user == user2}")

    // hashCode() function
    println(user.hashCode())
    println(secondUser.hashCode())
    println(thirdUser.hashCode())

    // copy() function
    println(user.copy())
    println(user === user.copy())
    println(user.copy(name = "Max"))
    println(user.copy(id = 3))

    println("name = ${user.component1()}")
    println("id = ${user.component2()}")
}