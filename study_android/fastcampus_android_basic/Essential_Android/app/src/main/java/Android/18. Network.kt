package Android

/*
네트워크
-> 통신

DB ----> Server <----> Client(App, Web)

Local DataBase의 한계
- 동기화가 어렵다.
- 상호작용이 불가능하다.


서버와 통신하는 방법 (클라이언트에서 하는일)
- 해당 url로 요청한다.
- 인증정보를 보낸다.
- JSON 형식을 사용해서 data를 보낸다.
- JSON(JavaScript Object Notation) -> Javascript에서 객체를 만들 때 사용하는 표현식

- java, kotlin와 같은 언어들은 강타입 언어라고 한다.
변수 생성 시 타입을 반드시 작성해야함. (java는 특히)
public int a = 10 (10은 반드시 Int타입)

그러나 kotlin은 반 강타입
ex) val number = 10 (10을 Int로 추론)



JSON 형식
- [] -> List
- {} -> 객체

JSON Response
[
    {
        "id" : 1,           -> 타입이 문서에 써있다.
        "name" : "홍길동",
        "age" : 20,
        "intro" : "나는 홍길동이다!"
    },
        {
        "id" : 1,
        "name" : "김아무개",
        "age" : 25,
        "intro" : "나는 김아무개이다!"
    }
]

서버개발자들이 이런 API를 만들고 나서 클라이언트 개발자에게 해당 문서를 넘겨준다.
API요청을 하면 어떤 JSON Response가 오며 해당 필드는 어떤 타입이고 어떤 뜻인지를 알 수 있다.

JSON Parsing
-> Json을 코틀린이나 자바가 이해할 수 있게 변형 하는 과정

JSON Parsing을 위해선 Serializable(직렬화)를 해야한다.
-> 자바 시스템 내부에서 사용되는 object를 외부에서 사용할 수 있도록 byte 형태로 바꾸는 것.

코틀린 코드에 다음과 같이 클래스가 정의되어있다.
class Person(
    var id : Int? = null
    var name : String? = null
    var age : Int? = null
    var intro : String? = null
)
그러면 JSON Reponse로 온 id, name, age, intro 데이터들이
각각 Person클래스 내부 필드에 맞게 저장되고
Person(1, "김아무개", 20, "안녕하세요") 인스턴스가 생성되고
클라이언트 개발자가 생성된 인스턴스를 사용한다.

Request Type                        Status Code
- GET       -> 정보 요청            -> 200 OK
- POST      -> 정보 추가 요청        -> 201 CREATED
- DELETE    -> 정보 삭제 요청
- PUT       -> 정보 수정 요청

Network Library
- Volly
- Retrofit


 */