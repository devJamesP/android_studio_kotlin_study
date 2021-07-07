package kotlincontents.collections

/*
Set은 순서가 없는 컬렉션입니다. 해당 컬렉션은 중복을 허용하지 않습니다. Set을 생성할 때,
setOf()메서드와 mutableSetOf() 메서드를 호출합니다. mutable set을 Set으로 캐스팅 하여 가져올 수 있습니다.
 */

val openIssues: MutableSet<String> = mutableSetOf("uniqueDescr1", "uniqueDescr2", "uniqueDescr3") // 1

fun addIssue(uniqueDesc: String): Boolean {
    return openIssues.add(uniqueDesc)                                                             // 2
}

fun getStatusLog(isAdded: Boolean): String {
    return if (isAdded) "registered correctly." else "marked as duplicate and rejected."          // 3
}

fun main() {
    val aNewIssue: String = "uniqueDescr4"
    val anIssueAlreadyIn: String = "uniqueDescr2"

    println("Issue $aNewIssue ${getStatusLog(addIssue(aNewIssue))}")                              // 4
    println("Issue $anIssueAlreadyIn ${getStatusLog(addIssue(anIssueAlreadyIn))}")                // 5
}

/*
1. elements가 있는 Set 생성
2. 실질적으로 더해진 elements가 있다면 해당 Boolean값을 반환함
3. 파라미터로 함수의 결과에 기반한 string 반환
4. 성공 메시지 출력: 성공적으로 Set에 추가됨
5. 실패 메시지 출력: 중복된 element가 존재하므로 추가될 수 없음
 */