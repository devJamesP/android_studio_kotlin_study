package kotlincontents.specialclasses

import java.util.*


/*
코틀린에서 킅래스들과 오브젝트들은 대부분의 object-oriented 언어들과 동일하게 동작합;니다.
클래스는 blueprint, object는 instance of class입니다. 항상 클래스를 정의한다음
해당 클래스의 여러 인스턴스를 만듭니다.
 */

//class LuckDispatcher { // 1
//    fun getNmuber() { // 2
//        var objRandom = Random()
//        println(objRandom.nextInt(90))
//    }
//}
//
//fun baekjoon.main() {
//    val d1 = LuckDispatcher() // 3
//    val d2 = LuckDispatcher()
//
//    d1.getNmuber() // 4
//    d2.getNmuber()
//}

/*
1. 클래스를 정의
2. 메소드를 정의
3. 인스턴스 생성
4. 인스턴스의 메서드 호출

코틀린에는 object keyword가 있으며, single implementation으로 데이터의 타입을 얻는데 사용됩니다.

만약 자바 사용자이고 "single"이 어떤 의미인지 안다면, 싱글턴 패턴을 의미합니다. 이것은
2개 이상의 쓰레드에서 생성되도록 하는 시도조차도 하나의 해당 클래스의 인스턴스가 하나만 생성하도록 보장합니다.

object로 선언된다면 이는 클래스도 아니고 생성자도 아니고 lazy한 인스턴스만 있으면 됩니다.
lazy인 이유는 객체에 접근될 때 생성되기 때문입니다. 그렇지 않으면 생성되지 않습니다.
 */

/* object Expression
object Expression 기본적인 사용법은 다음과 같습니다. 이는 간단한 objects/properties 구조입니다.
여기에 클래스 선언은 필요하지 않습니다. single object는 생성하고, 멤버들을 선언하고, 하나의 함수 내에서
접근하도록 합니다. 자바의 익명 클래스처럼 생성합니다.
 */

//fun rentPrice(standardDays : Int, festivityDays : Int, specialDays : Int) : Unit {
//    val dayRates = object {
//        var standard : Int = 30 * standardDays
//        var festivity : Int = 50 * festivityDays
//        var special : Int = 100 * specialDays
//
//        fun test() = 0
//    }
//
//    val total = dayRates.standard + dayRates.festivity + dayRates.special
//    val test = dayRates.test()
//    print("Total price : $$total")
//}
//
//fun baekjoon.main() {
//    rentPrice(10, 2, 1)
//}

/* object 프로퍼티에 다음과 같이 접근 할 수 있습니다.
또한 object 키워드를 사용한 객체 내부에는 멤버 메서드 역시 직접 접근이 가능합니다.
 */


/*
companion object는 구문 상 java의 static 메서드와 유사합니다. 해당 오브젝트를 호출하면
클래스 이름 한정자로써 사용 할 수 있습니다. 사용할거면 패키지 수준 함수 대신에 사용하세요.
 */

//class BigBen {
//    companion object Booger {
//        fun getBongs(nTimes : Int) {
//            for(i in 1..nTimes) {
//                print("Bong ")
//            }
//        }
//    }
//}
//
//fun baekjoon.main() {
//    BigBen.getBongs(10)
//}

