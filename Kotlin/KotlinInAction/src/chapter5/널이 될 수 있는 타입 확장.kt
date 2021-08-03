package chapter5

import java.lang.IllegalArgumentException

/**
 * isEmpty, isBlank같은 확장함수는 해당 객체가 ""인지 whithspace로 이루어져있는지 검사하는데
 * 여기에 더해 null까지 검사하는 확장함수는 isNullOrEmpty, isNullOrBlank가 있다.
 */

fun<T> verifyUserInput(input : T) {
    if (input is String?) {
        if (input.isNullOrEmpty()) { // safe call을 하지 않아도 됨. input?.isEmpty() 이런...
            println("please fill in the required fields.")
        }
    }
}

// 동일한 결과
//verifyUserInput(null)
//verifyUserInput("")