package chapter5


/** 알파벳 만들기(with, apply) */
fun alphabet(): String {
    val result = StringBuilder()
    for (alpha in 'A'..'Z') {
        result.append(alpha)
    }
    result.append("\nNow I know the alphabet!")
    return result.toString()
}

fun alphabetWith() = with(StringBuilder()) {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
    toString()
}

fun alphabetApply() = StringBuilder().apply {
    for(letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()

fun alphabetBuildString() = buildString {
    for(letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}
