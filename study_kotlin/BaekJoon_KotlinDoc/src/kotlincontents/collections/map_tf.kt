package kotlincontents.collections

/*
map 확장 함수는 컬렉션의 모든 elements를 변형하여 적용시켜준다.
람다 파라미터로 함수를 변환합니다.
 */


val doubled = numbers.map { x -> x * 2 }      // 2
val tripled = numbers.map { it * 3 }          // 3

fun main() {
    println(doubled)
    println(tripled)
    println(numbers)
}

/*
2. 2배가 적용된 numbers2 정의
3. it 표기법으로 간결하게 표기
 */