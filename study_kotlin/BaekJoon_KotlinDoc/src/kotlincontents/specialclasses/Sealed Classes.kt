package kotlincontents.specialclasses

/*
Sealed classes는 상속을 제한 할 수 있습니다. 하나의 seal class를 선언한다고 하면,
오직 같은 파일 내부에서만 sealed class가 선언된 곳에서 서브 클래스들이 선언될 수 있습니다.
sealed class가 선언부 파일 외부에서 서브클래스들은 선언될 수 없습니다.
 */

sealed class Mammal(val name: String) // 1

class Cat(val catName: String) : Mammal(catName) // 2
class Human(val humanName: String, val job: String) : Mammal(humanName)

fun greetMammal(mammal: Mammal): String = when (mammal) { // 3
    is Human -> "Hello ${mammal.name}; You`re working as a ${mammal.job}" // 4
    is Cat -> "Hello ${mammal.name}" // 5, 6
}

fun main() {
    println(greetMammal(Human("JamesPark", "AndroidDev")))
}
/*
1. sealed class 정의
2. 서브 클래스 정의, 모든 서브클래스는 반드시 같은 파일내에서 정의해야 합니다.
3. when(argument)식의 argument로 실드 클래스의 인스턴스를 사용
4. Mammal -> Human으로 스마트 캐스팅 수행
5. Mammal -> Cat으로 스마트 캐스팅 수행
6. sealed class의 서브클래스들은 모든 하위클래스가 다루어지기 때문에 else-case가 필요하지 않습니다.
 */