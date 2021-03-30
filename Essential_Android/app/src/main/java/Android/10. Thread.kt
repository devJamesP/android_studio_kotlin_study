package Android

/*
Thread(쓰레드)
- 작업 흐름

앱실행 ---> launcher Activity -----> 작업 흐름

안드로이드의 쓰레드
-> MainThread(앱이 실행되서 끝날때까지 실행 됨)
----------------------------------------------------------->
        -> launcher Activity
                -> Acivity B
                        -> 영상 재생
                                    -> 기타 등등

메인 쓰레드는 반드시 존재해야 하지만 다른 쓰레드는 개발자가 인위적으로 만들 수 있음.
쓰레드가 여러개 있으면 작업을 동시에 할 수 있기에 좋다.
--------->더하기--------->빼기--------->나누기--------->곱하기


<쓰레드 사용 시>
------------->더하기
        -------------------------->빼기
        ---------------곱하기
                            ---------------------->나누기

안드로이드 MainThread의 특징
- UI(User Interface) Thread
    - 사용자의 input을 받는 쓰레드
- 절대 정지시킬 수 없다.(없다기 보다는 하면 안됨!!(충돌남))
    - 왜냐하면 정지 시키거나 종료 시키면 더 이상 사용자의 input을 받을 수 없음. -> 그래서 강제종료됨.
 */