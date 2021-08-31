package kotlincontents.delegation_pattern

/**
 * 표준 코틀린 라이브러리는 유용한 델리게이트들을 포함하고 있습니다.
 * lazy, observable 등등..
 * lazy의 경우 초기화를 할 때 사용합니다.
 */

class LazySample {
    init {
        println("created!")
    }
    val lazyStr: String by lazy {
        println("computed!")
        "my lazy"
    }
}

fun main() {
    val sample = LazySample()
    println("lazyStr = ${sample.lazyStr}")
    println(" = ${sample.lazyStr}")
}

/*
lazy 프로퍼티는 객체가 생성될 때 초기화를 하지 않습니다.
처음 get() 호출 시(해당 lazy객체를 호출) 람다식 내부를 인수로 실행하고 마지막 결과 저장합니다.
이후 get()호출 시에는 저장된 결과를 반환합니다.(여기서는 "my lazy")
 */