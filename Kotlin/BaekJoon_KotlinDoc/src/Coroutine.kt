import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/* non-blocking(delay)함수와 쓰레드를 잠시 멈추는 Thread.sleep()은 엄연히 다르다.
non-blocking한다는 말은 현재 쓰레드를 중단시키지 않는다는 의미이다.
blocking한다는 말은 현재 쓰레드를 중단시킨다는 의미이다.

ex)
delay(1000L)을 호출한다고 하더라도
현재 스레드의 동작은 멈추지 않는다. (non-blocking)

Thread.sleep(1000L)을 호출하면 현재 스레드는 잠시 중단된다.

안드로이드에 적용해보면, 메인스레드를 sleep()메소드로 오랬동안 중지한다면 문제가
될 수 있다. 다만 어떤 코루틴을 생성하여 delay함수를 호출한다면 문제가 되지 않는다.
왜? non-blocking 함수이기 때문에!!!


GlobalScope라는 말처럼 해당 스코프는 실행중인 쓰레드의 환경에 적용된다.
즉, launch 또는 async된 코루팀은 해당 애플리케이션 전체의 생명주기에 적용된다는 말이다.

suspend함수는 기본적으로 일반 스레드에서 사용할 수 없는데 그럼에도 runBlocking { }사용할 수 있는 이유는
runBlocking{ }도 내부적으로 코루틴을 생성해서 작동하기 때문이다. 다만!! 코루틴을 생성해서 동작함과 동시에
현재 자신이 속한 스레드를 blocking하기 때문이다!!!!!!!!
 */

/* 기본 예제 */ /* main문으로 바꿔서 실행 */
fun basicCoroutine() {
    GlobalScope.launch { // 새로운 코루틴을 백그라운드에서 열고 진행
        delay(2000L)
        println("World!")
    }
    println("Hello,") // 메인쓰레드에서 즉시 실행

    runBlocking { // mainThread를 block시킴 (명칭 그대로 block을 실행한다는 의미)
        delay(1000L) // 2초동안에 JVM이 살아있음(메인쓰레드 종료 안됨)
        println("finish")
   }
}
/*
처음부터 runBlocking를 메인 쓰레드(main문)에서 return 함으로써 메인스레드를 블록시키고
top-level 코루틴을 시작한다. runBlocking{ ... } 블록 안에있는 모든 코루틴들이 완료될때까지
자신이 속한 스레드를 종료시키지 않고 블락시킨다.


runBlocking을 메인스레드 전체에 걸어줌으로써 시작부터 메인 쓰레드를 블락시키고
top-level 코루틴을 시작한다. 위에서 설명했듯이 runBlocking은 runBlocking {...} 블록 안에있는
 모든 코루틴들이 완료될때 까지 자신이 속한 스레드를 종료시키지 않고 블락시킨다.
 따라서 runBlocking에서 가장 오래 걸리는 작업인 delay(2초)가 끝날 때 까지 메인쓰레드는 죽지 않고 살아있다.

 */
fun baseKotlin2() = runBlocking<Unit> { // start baekjoon.main coroutine
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // baekjoon.main coroutine continues here immediately
    delay(2000L)      // delaying for 2 seconds to keep JVM alive
}

/*
그런데 1초의 시간뒤에 "World!"라는 단어를 찍기 위하여 2초를 기다리는 일은 좋아보이지 않다.
DB의 데이터를 읽어오는 비동기 처리 작업이 있다면, 그때 걸리는 시간이
무조건 1초가 걸린다고 가정할 수 없고, 구체적인 시간동안 스레드를 죽이지 않는것도 좋지 못하다.
디비를 갔다가 오는 시간이 3초가 걸릴수도 있지 않은가. 따라서 우리는 db를 갔다가 어떤 응답을
가져오면, 그 즉시 어떤 일을 처리하고 프로그램을 종료시킬 방법이 필요하다.
 */

//fun baekjoon.main() = runBlocking {
//    val job = GlobalScope.launch { // 새로운 코루틴을 launch하고, job으로 참조한다.
//        delay(1000L)
//        println("World!")
//    }
//    println("Hello, ")
//    job.join() // wait until child coroutine completes
//}

/*
위의 코드에는 1초의 딜레이 이후 "World!"가 찍히는 것을 보기위해 2초동안 프로그램을 종료시키지 않는 delay(2000L)라는
코드가 필요하지 않다. 위 코드는 GlobalScope.launch로 생성한 코루틴이 제 기능을 다 완수하는 즉시 프로그램을 종료한다.
job이라는 변수가 특정 코루틴의 레퍼런스를 가지고 있고, job.join()이 job이 끝나기를 계속 기다리기 때문이다.
job이 끝나지 않으면 runBlocking()으로 생성한 코루틴을 끝나지 않는다.
 */


/* 모든 코루틴 뷜더는 뷜더로 인해 생성되는 코드 블록 안에다가
CoroutineScope 객체를 추가한다. 위 코드에서는 runBlocking의 블록 안에서 GlobalScope로 코루틴을 만들어 launch 했지만,
GlobalScope를 사용하지 않고 runBlocking이 만든 코루틴 스코프와 같은 스코프로 코루틴을 만들 수 있다.
그냥 launch{ }를 하면 됨!!!!!! 그냥 launch {...}를 호출하면 바로 바깥의 스코프와 동일한 스코프에 생긴다. 게다가 바깥에
있는 코루틴은 안쪽에 있는 코루틴이 끝날때 까지 끝나지 않는다는 성질을 이용해서 코드를 더 깔끔하게 만들 수 있다.
 */

/*
※ 헷갈리지 말자!! launch{..}로 하면 단지 동일한 CoroutineContext이기 때문에
생명주기가 모든 코루틴이 끝날때까지 끝나지 않는것일뿐!!!!!!!!!
상위에 생성한 코루틴이 끝날때까지 기다리지는 않는다는것!!
 */
fun main() = runBlocking {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello, ")
}


/*
suspend & resume
* suspend : 현재의 코루틴을 멈춘다.(현재의 코루틴!!)
* resume : 멈춰있던 코루틴 부분을 다시 시작한다.
 */

//class MyViewModel: ViewModel() {
//    fun fetchDocs() {
//        get("dev.android.com") { result ->
//            show(result)
//        }
//    }
//}

/* 콜백을 제거하고 코루틴으로 작성 */

//// Dispatchers.Main
//suspend fun fetchDocs() {
//    // Dispatchers.IO
//    val result = get("developer.android.com")
//    // Dispatchers.Main
//    show(result)
//}
//// look at this in the next section
//suspend fun get(url: String) = withContext(Dispatchers.IO){/*...*/}

/* 위 함수에서 suspend함수 get이 제 역할(요청에 대한 처리)을 끝내면 메인쓰레드에 콜백으로 알려주는 것이 아니라,
그저 멈춰있던 suspended coroutine 부분을 시작하는 것이다.
이말이 먼말이냐면 지금 result에서 get요청을 보냈다. 요청에 대한 응답을 받아서 show()메서드를 호출해야하는데
잘못 작성하면 요청에 대한 응답이 오기전에 show()메서드가 호출될 수 있다.
따라서 !!!!!
get()함수가 끝나고 반환해야 비로소 show()가 호출되어야 하는데 여기서 get은 콜백함수가 아니라는 것이다!!!
단지!! 잠시 fetchDocs()메서드를 중단했다가 네트워크를 통한 요청이 끝나면
중단했던 fetchDocs()의 suspended coroutine 부터 다시 실행되는 것일 뿐이다.

코루틴은 스스로 suspend(중단)할 수 있으며 dispatcher는 코루틴을 resume하는 방법을 알고 있다.
(중단된 부분부터 다시 시작할 수 있는 이유에 대한 간단한 설명같다.)
 */

/*
자주하는 오해
함수 앞에 suspend를 적는다고 해서 그것이 함수를 백그라운드 스레드에서 실행시킨다는 뜻은 아니다.
코루틴은 메인 쓰레드 위에서 돌며, 메인스레드에서 하기에는 너무 오래걸리는 작업을 하기 위해
코루틴의 Default나 IO dispatcher에서 관리한다. 코루틴이 메인 스레드 위에서 실행되더라도 꼭
dispatcher에 의해서 동작해야만 한다.

dispatcher
coroutine context는 어떤 스레드에서 해당 coroutine을 실행할지에 대한 dispatcher정보를 담고 있다.
즉, dispatcher에 따라서 실행되는 스레드가 달라진다.

 */