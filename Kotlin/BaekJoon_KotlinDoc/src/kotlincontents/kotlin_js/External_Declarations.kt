package kotlincontents.kotlin_js

/**
 * extarnal 키워드를 사용하면 type-safe한 방식으로 JS API를 선언할 수 있습니다.
 */

external fun alert(msg: String)   // 1

fun main() {
    alert("Hi!")                    // 2
}

/* 1. Kotlin은 컴파일 중에 String 유형의 단일 인수가 전달되었는지 확인합니다.
이러한 검사는 순수 JavaScript API를 사용하는 경우에도 일부 버그를 방지합니다.
 */