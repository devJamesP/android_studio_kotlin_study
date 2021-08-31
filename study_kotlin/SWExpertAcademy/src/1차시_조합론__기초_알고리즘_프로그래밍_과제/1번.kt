package `1차시_조합론__기초_알고리즘_프로그래밍_과제`

/** 52장의 카드에서 5장 조합 만드는 경우의수 ? */
fun main() {
    val charCard = mutableListOf<Char>()

    // 대문자
    for (i in 65..90) {
        charCard.add(i.toChar())
    }

    // 소문자
    for (i in 97..122) {
        charCard.add(i.toChar())
    }

    // 카드 확인
    println(charCard)

    /** 조합 공식 => nCr : n! / (n - r)!r!  */
    var count = 0
    for (one in 0 until charCard.size) {
        for (two in one + 1 until charCard.size) {
            for (three in two + 1 until charCard.size) {
                for (four in three + 1 until charCard.size) {
                    for (five in four + 1 until charCard.size) {
                        println("${charCard[one]}, ${charCard[two]}, ${charCard[three]}, ${charCard[four]}, ${charCard[five]}")
                        count++
                    }
                }
            }
        }
    }

    // 조합 가짓 수
    println("combination size = $count")
}