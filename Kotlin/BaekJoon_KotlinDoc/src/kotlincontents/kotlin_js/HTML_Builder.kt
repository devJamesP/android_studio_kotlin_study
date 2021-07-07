package kotlincontents.kotlin_js

/**
 * 코틀린은 선언적 스타일로 구조화된 데이터 표현할 수 있는 옵션들을 제공합니다.
 * 다음은 형식이 안전한 Groovy 스타일 빌더의 예입니다. 이 예에서는 Kotlin의 HTML 페이지를 설명합니다.
 */

//val result = html {                                            // 1
//    head {                                                     // 2
//        title { +"HTML encoding with Kotlin" }
//    }
//    body {                                                     // 2
//        h1 { +"HTML encoding with Kotlin" }
//        p {
//            +"this format can be used as an"                   // 3
//            +"alternative markup to HTML"                      // 3
//        }
//
//        // an element with attributes and text content
//        a(href = "http://kotlinlang.org") { +"Kotlin" }
//
//        // mixed content
//        p {
//            +"This is some"
//            b { +"mixed" }
//            +"text. For more see the"
//            a(href = "http://kotlinlang.org") {
//                +"Kotlin"
//            }
//            +"project"
//        }
//        p {
//            +"some text"
//            ul {
//                for (i in 1..5)
//                    li { +"${i}*2 = ${i*2}" }
//            }
//        }
//    }
//}