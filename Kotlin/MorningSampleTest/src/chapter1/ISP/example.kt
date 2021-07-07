package chapter1.ISP

abstract class Bird {
    abstract fun cry()
}

interface Flyable {
    fun fly()
}

interface Swimmable {
    fun swim()
}

abstract class FlyableBird : Bird(), Flyable

class Eagle : FlyableBird() {
    override fun fly() {

    }

    override fun cry() {

    }
}

class Penguin : Bird(), Swimmable {
    override fun cry() {

    }

    override fun swim() {
        - 
    }
}