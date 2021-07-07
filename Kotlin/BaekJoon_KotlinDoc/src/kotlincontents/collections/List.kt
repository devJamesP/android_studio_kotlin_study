package kotlincontents.collections

/*
List는 정렬된 콜렉션의 아이템입니다. List는 Mutable(변하는 List)되거나 읽기 전용(List)중 하나가 될 수 있습니다.
리스트를 생성할 때, std lib함수 listOf()는 읽기 전용의 리스트이며, mutableListOf()는 변경 가능한 리스트입니다.
원치 않은 수정을 방지하려면 변경 가능한 List로 캐스팅하여 읽기 전용으로 가져옵니다.
 */

val systemUsers: MutableList<Int> = mutableListOf(1, 2, 3)        // 1
val sudoers: List<Int> = systemUsers                              // 2

fun addSystemUser(newUser: Int) {                                 // 3
    systemUsers.add(newUser)
}

fun getSysSudoers(): List<Int> {                                  // 4
    return sudoers
}

fun main() {
    addSystemUser(4)                                     // 5
    println("Tot sudoers: ${getSysSudoers().size}")               // 6
    getSysSudoers().forEach {                                     // 7
            i -> println("Some useful info on user $i")
    }
    // getSysSudoers().add(5) <- Error!                           // 8
}

/*
1. MutableList 생성
2. 읽기 전용 List 생성
3. MutableList에 새로운 아이템 추가
4. 변하지 않는 list를 반환
5. MutableList를 업데이트, 모든 연관된(힙에 저장된 해당 데이터를 참조하고 있는) 읽기 전용 뷰들은
같은 오브젝트를 가리키는 동안에는 업데이트 되어집니다.
6. 읽기 전용의 리스트 사이즈를 검색
7. 리스트 반복 및 요소 출력
8. 읽기 전용 뷰에 쓰려고하면 컴파일 error가 발생합니다.
 */