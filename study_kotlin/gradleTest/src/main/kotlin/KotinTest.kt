import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin

import org.koin.dsl.module

// Data holder
data class HelloMessageData(val message : String = "Hello Koin!")

// Service
interface HelloService {
    fun hello(): String
}

// ServiceImplement
class HelloServiceImpl(private val helloMessageData: HelloMessageData): HelloService {
    override fun hello(): String = helloMessageData.message
}

// The application class
class HelloApplication : KoinComponent {
    val helloService by inject<HelloService>()

    fun sayHello() = println(helloService.hello())
}

// Declaring dependencies
val helloModule = module {
    single { HelloMessageData() }

    single { HelloServiceImpl(get()) as HelloService }
}


fun main(vararg args: String) {
    startKoin {
        // Koin Logger
        printLogger()
        // declare modules
        modules(helloModule)
    }

    HelloApplication().sayHello()
}