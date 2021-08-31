package `1차시_조합론__기초_알고리즘_프로그래밍_과제`

/** x + y + z = 100의 자연수 해 */
fun main() {
    var solutionCount = 0
    for (x in 1..98) {
        for (y in 1..(99 - x)) {
            println("$x, $y, ${(100 - x - y)}")
            solutionCount++
        }
    }
    println("해의 갯수 : $solutionCount")
}

