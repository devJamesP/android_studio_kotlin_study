package Android

/*
Fragment
- Activity -> 앱에 보이는 한 화면의 단위

- Activity가 가지고 있는 문제
Activity의 구성요소가 매우 길다면 코드가 길어져서 관리하기 힘들다

- 다양한 디바이스가 가지고 있는 문제
또한 테블릿에서는 스마트 폰과의 뷰 배치가 달라서 문제가 있다.
(가령 뷰의 속성을 1줄로 배치하기 보다는 2줄로 배치한다.)


사용처
- Activity의 파트를 나누어서 책입진다.

= 이를 해결하기 위해서 Fragment를 사용하면 좋다.

Fragment 특징
- 라이프사이클이 존재한다.
- Activity에 종속적이다.

사용방법
- XML에 ViewComponent로 추가한다.
- 코드로 동적으로 추가한다.

데이터 전달 방법
- Activity -> Fragment : argument와 bundle사용
- Fragment -> Activity : 자체 구현
 */