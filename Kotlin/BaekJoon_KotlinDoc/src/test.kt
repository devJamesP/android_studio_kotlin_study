import kotlincontents.specialclasses.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

var result = 0

data class Userr(val name: String, val age: Int)

fun main() = runBlocking {

    println("main 시작됨")
    coroutineJob()

    println("main 종료됨")
}

suspend fun coroutineJob() {
    CoroutineTask.taskStart<Int, Unit, Userr>(
        preExecute = {
            println("preExecute 실행")
        },
        doinBackground = {
            val a = it[0]
            println("결과 잘 나왔나? : ${it[0]}")
            Userr("홍길동", it[0])
        },
        postExecute = {
            println("이름 : ${it!!.name} 나이 : ${it.age}")
        }
    )
}

fun test1() {
    for (i in 0 until 1000000) {
        result += 1
    }
    println("일반 함수 test1의 result : $result")
}

fun test2() {
    for (i in 0 until 1000000) {
        result += 1
    }
    println("일반 함수 test2의 result : $result")
}

suspend fun test3() {
    for (i in 0 until 1000000) {
        result += 1
    }
    println("중단 함수 test3의 result : $result")
}

suspend fun test4() {
    for (i in 0 until 1000000) {
        result += 1
    }
    println("중단 함수 test4의 result : $result")
}


object CoroutineTask {
    private const val MSG_READY = 1000
    private const val MSG_PROGRESS = 2000
    private const val MSG_FINISH = 3000

    private var isCancel = false

    fun <T, S, V> taskStart(doinBackground: (ArrayList<T>) -> V?, preExecute: () -> Unit, postExecute: (V?) -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            val test = arrayListOf<T>()
            var test2: V? = null

            launch {
                try {
                    preExecute()
                    test2 = doinBackground(test)
                    postExecute(test2)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }.join()
        }
    }
}