package kotlincontents.productivity_boosters

import java.time.LocalDate
import java.time.chrono.ChronoLocalDate


/**
 * 코틀린은 자동으로 스마트하게 캐스팅을 합니다.
 *
 * 1. null을 허용하는 타입으로부터 null을 허용하지 않는 타입으로 대응되게 캐스팅 할 수 있습니다.
 * 2. superType을 subType으로 케스팅 할 수 있습니다.
 */

fun main() {
    val date : ChronoLocalDate? = LocalDate.now()

    if (date != null) {
        println(date.isLeapYear)
    }

    if (date != null && date.isLeapYear) {
        println("It`s a leap year!")
    }

    if (date == null || !date.isLeapYear) {
        println("There`s no Feb 29 this year...")
    }

    if (date is LocalDate) {
        val month = date.monthValue
        println(month)
    }
}