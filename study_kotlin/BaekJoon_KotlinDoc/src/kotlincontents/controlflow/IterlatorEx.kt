package kotlincontents

class Bus(
    val name: String,
    val power: String,
    val price: Int
) {
}

class Car(private val cars: List<Bus>) {
    private var index = 0
    operator fun iterator(): Iterator<Bus> = cars.iterator()
    operator fun next(): Bus = cars[index++]
    operator fun hasNext() : Boolean =
        if (index < cars.size) {
            true
        } else {
            index = 0
            false
        }
    override fun toString(): String {
        return "Bus${cars.joinToString()}"
    }
}

fun main() {
    val cars = Car(
        listOf(
            Bus("A", "1000", 10000),
            Bus("B", "1000", 10000),
            Bus("C", "1000", 10000),
            Bus("D", "1000", 10000)
        )
    )

//    for(i in cars) {
//        println(i.name)
//    }
//
//    while (cars.hasNext()) {
//        println(cars.next().name)
//    }
//
//    val x = 2
//    if (x in 1..5) {
//        println("x is in range from 1 to 5")
//    }
//    if (x !in 1..5) {
//        println("x is not in range from 1 to 5")
//    }

    val authors = setOf("Shakespeare", "Hemingway", "Twain")
    val writers = setOf("Twain", "Shakespeare", "Hemingway")
    val authors2 = setOf("Shakespeare", "Hemingway", "Twain")

    println(authors == writers)   // 1 true
    println(authors === writers)  // 2 false
    println(authors === authors2) // 3 false

}