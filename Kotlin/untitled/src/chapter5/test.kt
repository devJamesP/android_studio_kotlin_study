package chapter5

data class Person(val name: String, val age: Int)
data class Book(val title: String, val authors: List<String>)

fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0

    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors++
        } else if (it.startsWith("5")) {
            serverErrors++
        }
    }
    println("$clientErrors client errors, $serverErrors server errors")
}

fun main() {
    val people = listOf(Person("Alice", 25), Person("Bob", 30), Person("Carol", 30))
    val canBeInClub27 = { p: Person -> p.age >= 27 }
    val list = listOf("a", "ab", "b")


    val books = listOf(
        Book("저자1", listOf("김", "개", "똥")),
        Book("저자2", listOf("박", "무", "개")),
        Book("저자3", listOf("이", "서", "방"))
    )

    val strings = listOf("abc", "def")

    /** 시퀀스로 변환하여 술어를 적용하면 기존에 위의 결과에서는 중간 결과를 저장하는 컬렉션을 생성하지만
     * 밑의 식은 그렇지 않기 때문에 성능이 증가한다.
     */
    println(listOf(1,2,3,4).asSequence().map{ it * it }.find { it > 3})



}