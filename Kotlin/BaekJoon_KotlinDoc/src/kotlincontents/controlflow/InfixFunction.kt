package kotlincontents.controlflow

fun main() {
    val stack = mutableStackOf(0.62, 0.35, 0.22)
    println(stack)
}

class MutableStack<E>(vararg items : E) {
    private val elements = items.toMutableList()
    fun push(element : E) : Boolean = elements.add(element)
    fun peek() : E = elements.last()
    fun pop() : E = elements.removeAt(elements.size - 1)
    fun isEmpty() : Boolean = elements.isEmpty()
    fun size() : Int = elements.size
    
    override fun toString(): String = "MutableStack(${elements.joinToString()})"
}

fun <E> mutableStackOf(vararg elements : E) : MutableStack<E> = MutableStack(*elements)

