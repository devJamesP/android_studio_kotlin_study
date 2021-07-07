package kotlincontents.collections

/*
A Map은 키/값 쌍으로 이루어진 컬렉션이다. 각 키는 고유하고 그에 상응하는 값을 검색하여 사용할 수 있다.
맵을 생성할 때 mapOf()함수와 mutableMapOf()로 생성
infix 메서드인 to를 사용하여 쉽게 초기화할수 있습니다.
이 역시 MutableMap를 Map으로 캐스팅하면 읽기 전용의 뷰로 가져올 수 있습니다.
 */

const val POINTS_X_PASS: Int = 15
val EZPassAccounts: MutableMap<Int, Int> = mutableMapOf(1 to 100, 2 to 100, 3 to 100)   // 1
val EZPassReport: Map<Int, Int> = EZPassAccounts                                        // 2

fun updatePointsCredit(accountId: Int) {
    if (EZPassAccounts.containsKey(accountId)) {                                        // 3
        println("Updating $accountId...")
        EZPassAccounts[accountId] = EZPassAccounts.getValue(accountId) + POINTS_X_PASS  // 4
    } else {
        println("Error: Trying to update a non-existing account (id: $accountId)")
    }
}

fun accountsReport() {
    println("EZ-Pass report:")
    EZPassReport.forEach { (k, v) ->                                                    // 5
        println("ID $k: credit $v")
    }
}

fun main() {
    accountsReport()                                                                    // 6
    updatePointsCredit(1)                                                     // 7
    updatePointsCredit(1)
    updatePointsCredit(5)                                                     // 8
    accountsReport()                                                                    // 9
}

/*
1. MutableMap을 생성
2. 읽기 전용의 Map을 생성
3. Map의 키가 있는지 확인
4. 키에 상응하는 값을 읽고, 포함된 값을 증가시킴
5. 변경 불가한 Map을 Iterate하고, 키와 값의 쌍을 출력
6. account points balance를 읽고 (업데이트 전)
7. 존재하는 키의 account point를 업데이트
8. 존재하지 않는 계정값의경우 업데이트 시도 시 error message 출력
9. 현재 계정의 포인트를 read, (업데이트 후)
 */