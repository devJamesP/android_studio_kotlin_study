package Android

/*
Task

- Stack
 위쪽 방향으로 Task가 쌓임.
 Task가 쌓임

                                                       A
 A ------> B -------> C -------> B -------> A -------> B
           A          B          A          B
                      A                     A



켜지는 방법을 자체 속성으로 가지고 있는 경우
-> launchMode

켜지는 방법을 지시하는 경우
-> IntentFlag

LaunchMode          다중 허용
- Standard              O
- singleTop         조건부 (열려고 하는 액티비티가 현재 액티비티라면
                        onNewIntent를 호출함)
----------------------------------------------------
- singleTask            X
- singleInstance        X

인텐트 플레그
FLAG_ACTIVITY_NEW_TASK
FLAG_ACTIVITY_SINGLE_TOP
FLAG_ACTIVITY_CLEAR_TOP

※ 최대한 런치모드나 인텐트 플래그는 웬만하면 사용 하지 않는게 좋다.
기본 스택 방식으로 앱이 동작하는게 좋으며,
사용하더라도 완벽하게 숙지하고 활용할 수 있을때 사용해야한다.

 */