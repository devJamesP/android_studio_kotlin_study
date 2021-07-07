package kotlincontents.specialclasses

/*
Enum classes는 finite set을 represent하는 model types 예를 들어, 방향, 상태, 모드 등
 */

//enum class State {
//    IDLE, RUNNING, FINISHED
//}
//
//fun baekjoon.main() {
//    val state = State.RUNNING
//
//    val message = when (state) {
//        State.IDLE -> "It`s idle"
//        State.RUNNING -> "It`s running"
//        State.FINISHED -> "It`s finished"
//    }
//    println(message)
//}
/*
1. 3개의 enum instances를 갖는 간단한 enum class를 정의했습니다. enum class는 유한하며 각각 구분됩니다.
2. enum class의 인스턴스는 클래스이름을 통해 접근 가능합니다.
3. 컴파일러가 when 식에서 enum class는 유한하므로 else -case가 필요하지 않다고 추론합니다.
 */


/*
Enums는 다른 클래스들처럼 프로퍼티와 메서드를 가질 수 있습니다. 또한 새미콜로에 의해 리스트의 인스턴스들이 구분되어집니다.
 */

enum class Color(val rgb : Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF),
    YELLOW(0xFFFF00);

    fun containsRed() = (this.rgb and 0xFF0000) != 0
}

fun main() {
    val red = Color.RED
    println(red)
    println(red.containsRed())
    println(Color.BLUE.containsRed())
    println(Color.YELLOW.containsRed())
}
/*
1. enum class는 프로퍼티와 메서드를 함께 정의할 수 있습니다.
2. 각 인스턴스는 constructor parameter를 위한 argument를 pass 해야합니다.
3. enum class 멤버들은 세미콜론으로 정의된 인스턴스들이 구분됩니다.
4. "RED"처럼 기본적으로 toString값은 인스턴스의 이름을 반환합니다.
5. enum instance는 메소드를 호출 할 수 있습니다.
6. enum class 이름을 통해 메소드를 호출 할 수 있습니다.
 */