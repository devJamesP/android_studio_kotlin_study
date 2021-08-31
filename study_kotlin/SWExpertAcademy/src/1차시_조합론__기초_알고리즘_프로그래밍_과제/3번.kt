package `1차시_조합론__기초_알고리즘_프로그래밍_과제`

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.pow

/** m개의 원소를 가진 집합에서 n개의 원소를 가진 집합으로 가는 전사함수의 개수 */

// 공식 : n^m

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (m, n) = br.readLine().split(" ").map { it.toInt() }

    if (m < n) {
        println("전사함수가 될 수 없습니다.")
        return
    }

    println("정의역(${m}) -> 공역(${n})으로 가는 전사함수의 갯수 : ${(n.toDouble()).pow(m).toInt()}")

}