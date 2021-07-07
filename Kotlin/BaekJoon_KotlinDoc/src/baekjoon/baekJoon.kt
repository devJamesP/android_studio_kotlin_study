package baekjoon

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    // BufferReader, BufferWriter
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val n = br.readLine()
    bw.write("${getConstructor(n)}")

    bw.flush()
    br.close()
    bw.close()
}

fun getConstructor(num : String) : Int {
    for(i in num.toInt() - 9*num.length until num.toInt()) {
        val nSize = i.toString().length
        if (num.toInt() == getDivNum(i, nSize)) return i
    }
    return 0
}

fun getDivNum(n : Int, nSize : Int) : Int {
    var div = 1
    for (i in 0 until nSize) {
        div *= 10
    }
    var sum = n
    while(div > 1) {
        sum += n % div / (div / 10)
        div /= 10
    }
    return sum
}