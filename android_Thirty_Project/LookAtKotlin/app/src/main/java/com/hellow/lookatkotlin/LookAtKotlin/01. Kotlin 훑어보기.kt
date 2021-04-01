package com.hellow.lookatkotlin.LookAtKotlin

/*
1. Scope Function (apply, with, let, also, run)

Apply 함수 : 객체 확장 함수
val person = Person().apply {
    firstName = "Fast"
    lastName = "Campus"
}
상기된 firstName, lastName에 this없이도 작성 할 수 있음.

Person person = new Person();
person.firstName = "Fast";
person.lastName = "Campus";

자바로 작성된 코드임.

apply함수는 인스턴스 내부의 프로퍼티에 접근 할 수 있으며 반환값이 인스턴스 자신이므로
주로 초기화할때 사용


Also 함수
Random.nextInt(100).also {
    print



 */