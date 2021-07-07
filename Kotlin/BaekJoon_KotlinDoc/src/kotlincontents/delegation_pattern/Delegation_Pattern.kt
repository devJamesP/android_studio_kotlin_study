package kotlincontents.delegation_pattern

/**
 * 코틀린은 보일러 플레이트코드 없이 native 레벨에서(저수준?) 델리게이트 패턴 구현을 쉽게 하도록 지원합니다!!
 */

interface SoundBehavior {                                                          // 1
    fun makeSound()
}

class ScreamBehavior(val n:String): SoundBehavior {                                // 2
    override fun makeSound() = println("${n.toUpperCase()} !!!")
}

class RockAndRollBehavior(val n:String): SoundBehavior {                           // 2
    override fun makeSound() = println("I'm The King of Rock 'N' Roll: $n")
}

// Tom Araya is the "singer" of Slayer
class TomAraya(n:String): SoundBehavior by ScreamBehavior(n)                       // 3

// You should know ;)
class ElvisPresley(n:String): SoundBehavior by RockAndRollBehavior(n)              // 3

fun main() {
    val tomAraya = TomAraya("Thrash Metal")
    tomAraya.makeSound()                                                           // 4
    val elvisPresley = ElvisPresley("Dancin' to the Jailhouse Rock.")
    elvisPresley.makeSound()
}
/*
1. SoundBehavior 인터페이스 정의
2. 인터페이스 구현 클래스들
3. TomAraya, ElvisPresley는 두 인터페이스를 구현하고 있지만, 메소드는 없다. 대신에
댈리게이트가 인터페이스 메소드를 호출합니다.
4. makeSound()호출 시 호출은 델리게이트 객체에게 위임됩니다.
 */