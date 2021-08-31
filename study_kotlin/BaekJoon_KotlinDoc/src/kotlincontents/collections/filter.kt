package kotlincontents.collections

/*
filter는 컬렉션의 필터링을 가능하게 해준다.
filter predicate(필터 조건자)를 람다식의 파라미터로써 사용합니다.
각 element에 조건을 적용합니다. elements는 조건이 true일때 이를 컬렉션 결과에 반환합니다.
 */

val numbers = listOf(1, -2, 3, -4, 5, -6)       // 1
val positives = numbers.filter { x -> x > 0 }   // 2
val negatives = numbers.filter { it < 0 }       // 3

fun main() {
    println(positives)
    println(negatives)
}
/*
1. number 컬렉션을 정의
2. 양수를 필터링
3. it 노테이션으로 음수를 필터링
 */