package chapter8

fun twoAndThree (operation: (Int, Int) -> Int) {
    val result = operation(2, 3)
    println("The result is $result")
}

fun String.filter(predicate: (Char) -> Boolean): String {
    val sb = StringBuilder()
    for (index in 0 until this.length) {
        val element = get(index)

        if (predicate(element)) sb.append(element)
    }
    return sb.toString()
}

fun<T> Collection<T>.filter(predicate: (T) -> Boolean): List<T> {
    val list = arrayListOf<T>()
    for(element in this) {
        if (predicate(element)) {
            list.add(element)
        }
    }
    return list
}


/**356~7p joinToString 확장 함수 람다식 null, 기본값 대응 */
fun <T> Collection<T>.joinToStringDefault(
    seperator: String = ", ",
    prefix: String = "",
    postfix: String = "",
    transform: (T) -> String = { it.toString() }
): String {
    val result = StringBuilder(prefix)
    for((index, element) in this.withIndex()) {
        if (index > 0) {
            result.append(seperator)
        }
        result.append(transform(element))
    }
    result.append(postfix)

    return result.toString()
}


fun <T> Collection<T>.joinToStringNull(
    seperator: String = ", ",
    prefix: String = "",
    postfix: String = "",
    transform: ((T) -> String)? = null
): String {
    val result = StringBuilder(prefix)
    for((index, element) in this.withIndex()) {
        if (index > 0) {
            result.append(seperator)
        }
        var str = transform?.invoke(element)
            ?: element.toString()
        result.append(str)
    }
    result.append(postfix)

    return result.toString()
}


/**358p 함수를 반환하는 함수 정의하기 */
enum class Delivery { STANDARD, EXPEDITED }

class Order(val itemCount: Int)

fun getShippingCostCalculator (delivery: Delivery): (Order) -> Double {
    if (delivery == Delivery.EXPEDITED) {
        return { order -> 6 + 2.1 * order.itemCount }
    }
    return { order -> 1.2 * order.itemCount }
}


/** 359p 함수를 반환하는 함수를 UI 코드에서 사용하기
 * 이름이나 성이 D로 시작하는 연락처를 보기 위해 사용자가 D를 입력하면 prefix값이 변한다.
 * 연락처 목록 표시 로직과 연락처 필터링 UI를 분리하기 위해 연락처 목록을 필터링하는 술어 함수를 만드는 함수를 정의*/

data class Person(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String? = null
)

class ContactListFilters {
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false
    fun getPredicate(): (Person) -> Boolean { // 사람의 이름과 성을 검사 및 연락처에 전화번호가 있는지 검사
        // 성과 이름 둘 중에 prefix로 시작하는지 검사
        val startsWithPrefix = { p: Person ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        // 함수 타입의 변수를 반환
        if (onlyWithPhoneNumber.not()) {
            return startsWithPrefix
        }

        // 람다를 반환
        return { p -> startsWithPrefix(p) && p.phoneNumber != null }
    }
}

// 이부분은 꼭 봐둘것!! 람다식 자체로 넘기게 되면 코드의 길이를 대폭 줄일 수 있음!!
//fun main() {
//   val contacts = listOf(
//       Person("Dmitry", "Jamerov", "123-4567"),
//       Person("Svetlana", "Isakova", null)
//   )
//
//    val contactListFilters = ContactListFilters()
//    with(contactListFilters) {
//        prefix = "Dm"
//        onlyWithPhoneNumber = true
//    }
//
//    println(contacts.filter(contactListFilters.getPredicate()))
//}


/** 람다를 활용한 데이터 정의 :: 사이트 방문 데이터 정의 */
data class SiteVisit(
    val path: String,
    val duration: Double,
    val os: OS
)

enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

val log = listOf(
    SiteVisit("/", 34.0, OS.WINDOWS),
    SiteVisit("/", 22.0, OS.MAC),
    SiteVisit("/login", 12.0, OS.WINDOWS),
    SiteVisit("/signup", 8.0, OS.IOS),
    SiteVisit("/", 16.3, OS.ANDROID)
)
/** 하드 코딩한 필터 사용 */
fun averageWindowsDuraton() = log
    .filter { it.os == OS.WINDOWS }
    .map(SiteVisit::duration)
    .average()

/** 일반 함수를 사용하여 중복 제거 */
fun List<SiteVisit>.averageDurationFor(os: OS) =
    filter { it.os == os }.map(SiteVisit::duration).average()
/* 위의 확장 함수가 좋아보이지만, 만약 모바일 디바이스 사용자의 평균 방문
시간을 구하고 싶다면???
 */

/** 복잡하게 하드코딩한 필터를 사용해 방문 데이터 분석 */
fun averageMobileDuration() = log
    .filter { it.os in setOf(OS.ANDROID, OS.IOS) }
    .map(SiteVisit::duration)
    .average()

/* 여기서 한발 더 나아가 질의가 더 복잡해지면??
구현하기는 더 까다로워 지므로 이럴때 람다가 유용하다.
 */

/** 고차 함수를 사용해 중복 제거하기 */
fun List<SiteVisit>.averageDurationFor2(predicate: (SiteVisit) -> Boolean) =
    filter(predicate).map(SiteVisit::duration).average()
