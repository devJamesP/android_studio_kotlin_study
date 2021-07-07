@file:JvmName("Chapter1")

package test_package

import java.lang.IllegalStateException
import java.util.TreeMap
import test_package.Color.*
import java.io.BufferedReader
import java.lang.NumberFormatException
import java.lang.StringBuilder


/** enum class */
enum class Color(
    private val r: Int,
    private val g: Int,
    private val b: Int
) {
    RED(255, 0, 0), ORANGE(255, 165, 0),
    YELLOW(255, 255, 0), GREEN(0, 255, 0),
    BLUE(0, 0, 255), INDIGO(75, 0, 130),
    VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b
}

fun mix(c1: Color, c2: Color) = when {
    c1 == RED && c2 == YELLOW ||
            c1 == YELLOW && c2 == RED -> ORANGE
    else -> throw Exception("Dirty color")
}


/** TreeSample, when expression */
interface Expr

class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

class Num2(val value: Double) : Expr
class Multiple(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.left) + eval(e.right)
        else -> throw IllegalStateException("Unknown expression")
    }


/** in */
val binaryReps = TreeMap<Char, String>()

fun isLetter(c: Char) = c in 'A'..'Z' || c in 'a'..'z'
fun isNotDigit(c: Char) = c !in '0'..'9'
fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It`s a digit!"
    in 'a'..'z' -> "It`s a letter!"
    else -> "I don`t know..."
}

/** 예외 처리 */
fun exception(percentage: Int) {
    if (percentage !in 0..100) {
        throw IllegalStateException(
            "A percentage value must be between 0 and 100: $percentage"
        )
    }
}

fun readNumber(reader: BufferedReader): Int? {
    try {
        val line = reader.readLine()
        return Integer.parseInt(line)
    } catch (e: NumberFormatException) {
        return null
    } finally {
        reader.close()
    }
}

fun readNumber2(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    } catch (e: NumberFormatException) {
        return
    }
    println(number)
}

fun <T> joinToString2(
    collection: Collection<T>,
    seprator: String = ",",
    prefix: String = "[",
    postfix: String = "]"
): String {
    val result = StringBuilder(prefix)
    collection.forEachIndexed { index, element ->
        if (index > 0) result.append(seprator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}


/** 이렇게하면 자바에서 해당 joinToStringEVen()메소드를 호출할 때 자동으로 오버라이딩을 해주므로 편하다!! */
object Testt {
    @JvmOverloads
    fun <T> List<T>.joinToStringEVen(
        prefix: String = "{",
        separate: String = ";",
        postfix: String = "}",
        body: (T) -> Boolean
    ): String {
        if (this.isEmpty()) throw IllegalStateException("collection should have not null")
        val result = StringBuilder(prefix)

        this.forEachIndexed { i, e ->
            if (i % 2 == 0 && i > 0) result.append(separate)
            if (body(e)) result.append(e)
        }
        result.append(postfix)
        return result.toString()
    }
}

@JvmOverloads
fun <T> List<T>.joinToStringEVen(
    prefix: String = "{",
    separate: String = ";",
    postfix: String = "}",

    ): String {
    if (this.isEmpty()) throw IllegalStateException("collection should have not null")
    val result = StringBuilder(prefix)

    this.forEachIndexed { i, e ->
        if (i > 0) result.append(separate)
        result.append(e)
    }
    result.append(postfix)
    return result.toString()
}

const val UNIX_LINE_SEPARATOR = "\n"

@JvmOverloads
fun <T> Collection<T>.joinToString3(
    prefix: String = "{",
    seperated: String = ";",
    postfix: String = "}"
): String {
    if (this.isEmpty()) throw IllegalStateException("Collection should have not null.")
    val stringBuilder = StringBuilder(prefix)

//    this.forEachIndexed {i, e ->
//        if(i > 0) stringBuilder.append(seperated)
//        stringBuilder.append(e)
//    }

    for ((index, element) in this.withIndex()) {
        if (index > 0) stringBuilder.append(seperated)
        stringBuilder.append(element)
    }

    stringBuilder.append(postfix)

    return stringBuilder.toString()

}

fun Collection<String>.join(
    prefix: String = "{",
    seperated: String = ";",
    postfix: String = "}"
) = this.joinToString3(prefix, seperated, postfix)

/** 확장함수는 오버라이드 할 수 없다. (정적 메소드와 같은 특징을 가지므로) */

fun String.lastChar(): Char = get(length - 1)

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }


/** 가변인자!! 중위 호출!! */
infix fun <A, B> A.to2(that: B): Pair<A, B> = Pair(this, that)
fun <K, V> mapOf2(vararg values: Pair<K, V>): Map<K, V> = emptyMap()

/**substringBefore, substringBeforeLast, substringAfter 등을 활용한 path 자르기 */


/** 코드 중복을 피하기 위해 로컬 함수를 활용하자 !!! */
class User(val email: String? = null, val password: String? = null)






fun main() {


}

