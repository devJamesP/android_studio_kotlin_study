package `1차시_조합론__기초_알고리즘_프로그래밍_과제`

import java.io.BufferedReader
import java.io.InputStreamReader

/** m개의 원소를 가진 집합에서 n개의 원소를 가진 집합으로 가는 전사함수를 모두 출력 */


fun main() {
    // 숫자로 할거면 그냥 list생성 없이 해도 상관 없음.
    val domainList = mutableListOf<Int>() // 정의역
    val codomainList = mutableListOf<Int>() // 공역

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (m, n) = br.readLine().split(" ").map { it.toInt() }

    if (m < n) {
        println("전사함수가 될 수 없습니다.")
        return
    }

    // 정의역, 공역 setup
    for(i in 1..m) {
        domainList.add(i)
    }

    for(i in 1..n) {
        codomainList.add(i)
    }

    // 1. 정의역과 공역의 갯수가 같은 경우 n == m
   while(true) {

   }


    // 2. 정의역의 갯수가 공역의 갯수보다 많은 경우 m > n



}