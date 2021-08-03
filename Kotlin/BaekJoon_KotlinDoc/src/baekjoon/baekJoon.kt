package baekjoon

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

data class Person(val height: Int, val weight: Int)

fun main() {
    // BufferReader, BufferWriter
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (n, m) = br.readLine().split(" ").map { it.toInt() }
    val chessboard = MutableList(n * m) { ' ' }
    for (i in 0 until n) {
        val strChess = br.readLine()
        for (j in 0 until m) {
            chessboard[m * i + j] = strChess[j]
        }
    }

    var nModifyCount = 0

    val whiteChessBoard = MutableList(64) { "W" }
    val blackChessBoard = MutableList(64) { "B" }


    // whiteChessBoard인 경우
    for(i in 0 until n) {
        for(j in 0 until m) {
            if (j % 2 == 0 && chessboard[j] != 'W') nModifyCount++
            if (j % 2 != 0 && chessboard[j] != 'B') nModifyCount++
        }
    }

    // BlackChessBoard인 경우
    for(i in 0 until n) {
        for(j in 0 until m) {
            if (j % 2 == 0 && chessboard[j] != 'B') nModifyCount++
            if (j % 2 != 0 && chessboard[j] != 'W') nModifyCount++
        }
    }






    bw.flush()
    br.close()
    bw.close()
}
