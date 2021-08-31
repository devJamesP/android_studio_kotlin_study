package Android

/*
비동기 -> Async
동기 -> Sync

동기 방식
    - 작업을 순서대로 진행한다.
    - A -> B -> C -> D
    - 위에서 아래로 순차적으로 진행

비동기 방식
    - 쓰레드를 만들어서 작업을 따로 처리한다.

                     result     result
------------------------------------------> Main Thread
                    |       |
-----> Create Thread        |
                            |
            ---------------> Create Thread






안드로이드에서 Async 다루는 방법
        - AsyncTask 상속받는다.
            - onPreExcute      : 쓰레드 출발전에 할 작업
            - doIntBackground  : 쓰레드가 할 작업
            - onPregressUpdate : 중간중간에 MainThread로 온다(작업 결과를 중간보고, 근대 잘 안씀)
            - onPostExcute     : 작업을 다 마친후 MainThread로


Async의 장점
    - Main Thread를 기다리게 할 필요가 없다.
    - 네트워크 작업

Async의 단점
    - 재사용이 불가능하다
    - 구현된 Activity가 종료될 경우 따라서 종료가 되지 않는다. (정지하려면 Activity Life Cycle 활용(onPause))
    - AsyncTask는 하나만 실행될 수 있다. (병렬 처리가 안된다.)

     */