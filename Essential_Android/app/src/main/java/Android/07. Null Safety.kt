package Android

/*
Null Safety
-> Null에 대해서 안전하다.
-> Kotlin의 특징

Null이 안전하지 않은 이유
-> 0 + 10 = 10
-> Null + 10 = ?
-> Null.setOnClickListener{} -> 에러
-> NullPointExceptionError 발생

-> Kotlin이 Null Safety하기 위해서 사용하는 문법
-> ? = Null이 아니라면 이하 구문 실행
-> !! = Null이 x

Null이 될수 있는 타입
Int, Double, Class... -> Null이 될 수 없는 타입
Int?, Double?, Class? ... -> Null 허용

 */