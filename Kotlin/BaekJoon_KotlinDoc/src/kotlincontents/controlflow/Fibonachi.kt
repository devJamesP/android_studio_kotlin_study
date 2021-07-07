package kotlincontents

fun main() {
    val n = 10
    println(fibonacci(10))
}

fun fibonacci(n : Int) : Int{
    if(n <= 2) return 1
    return fibonacci(n - 1) + fibonacci(n - 2)
}