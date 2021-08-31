package kotlincontents.scope_fuctions


/**
 * 코틀린 표준 라이브러리 함수 let은 null-checks역할을 하는 스코프 함수입니다.
 * 객체를 호출할 때, 블록(람다식)이 주어지고 let을 실행하면 블록(람다식) 내의 마지막 표현식을 반환합니다.
 * 블록(람다식) 안에서 it을 통해 호출자(객체)를 참조할 수 있습니다.
 *
 * 즉, let은 null-check를 할 때 사용하고, 람다식 내에 호출한 객체를 it으로 참조하며, 람다식의 마지막 표현식을 반환합니다.
 */

fun main() {
    // 근대 이렇게 하면 굳이 let을 쓸 필요가 없음.
    val empty = "test".let {
        print(it)
        it.isEmpty() // isEmpty의 결과(true 또는 false)를 반환하여 empty에 할당
    }
    println(" is empty: $empty")


    printNonNull(null) // 인자로 null을 넘기므로 Printing "null"이 화면에 출력될거임
    printNonNull("안녕하세요.") // 인자로 null이 아닌 객체를 넘기므로 let 람다식이 실행됨

    // 두 인자 모두 null이 아니므로 let 블록 실행
    printIfBothNonNull("First", "Second")


}

fun printNonNull(str: String?) {
    println("Printing \"$str\":")

    // null을 체크하여 호출자 객체(str)가 null이 아니면 람다식을 실행하고, println() 반환값(void)을 반환
    str?.let {
        print("\t")
        print(it)
        println()
    }
}

fun printIfBothNonNull(strOne: String?, strTwo: String?) {
    strOne?.let { firstString ->
        strTwo?.let { secondString ->
            print("$firstString : $secondString")
            println()
        }
    }
}



