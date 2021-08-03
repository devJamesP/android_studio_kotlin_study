package chapter4

import java.io.File
import java.lang.IllegalArgumentException

interface User {
    val nickname: String

}

class PrivateUser(override val nickname: String) : User

class SubscribingUser(val email: String) : User {
    override val nickname: String
        get() = email.substringBefore('@')
}

class FacebookUser(val accountId: Int) : User {
    override val nickname: String = getFacebookName(accountId)

    fun getFacebookName(accountId: Int): String =
        "id : $accountId"
}

/*
추상 프로퍼티인 nickname을 오버라이드한 각 클래스에서
SubscribingUser클래스는 매번 nickname 프로퍼티를 호출 할 떄마다 substringBefore()메소드를
호출해야하고, FacebookUser클래스는 nickname필드에 해당 메소드의 결과를 저장했기에
메소드가 한번만 호출된다.

 */


class User3(val name: String) {
    var address: String = "unspecified"
        set(value) {
            println(
                """
            Address was changed for $name
            "$field" -> "$value".""".trimIndent()
            )
            field = value
        }
        get() = field + "as" +
                "df"
}

class Client(val name: String?, val postalCode: Int?) {
    override fun toString(): String =
        "Client($name $postalCode)"

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client) return false
        return this.name == other.name && this.postalCode == other.postalCode
    }

    override fun hashCode(): Int = name.hashCode() + postalCode.hashCode()

    fun copy(
        name: String? = this.name,
        postalCode: Int? = this.postalCode
    ): Client = Client(name, postalCode)
}

class DelegatingCollection<T> : Collection<T> {
    private val innerList = arrayListOf<T>()
    override val size: Int
        get() = innerList.size

    override fun contains(element: T): Boolean {
        return innerList.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return innerList.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return innerList.isEmpty()
    }

    override fun iterator(): Iterator<T> {
        return innerList.iterator()
    }
    fun add(element : T) {
        innerList.add(element)
    }
    fun add(vararg elements : T) {
        elements.forEach { e ->
            innerList.add(e)
        }
    }
}

// Collection<T> 인터페이스에서 구현해야 하는 프로퍼티와 메서드를 innerList(즉, ArrayList)에게 위임.
class Delegating2Collection<T>(
    innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList { }


// 클래스 위임 하고, 추가되는 기능 오버라이딩
class Counting<T>(
    private val innerSet : MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet {
    var objectAdded = 0
    override fun add(element: T): Boolean {
        objectAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectAdded += elements.size
        return innerSet.addAll(elements)
    }
}


object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File?, file2: File?): Int {
        if(file1 == null || file2 == null) throw IllegalArgumentException("argument should have not null.")
        return file1.path.compareTo(file2.path, ignoreCase = true)
    }
}

interface JSONFactory<T> {
    fun fromJSON(jsonText : String) : T
}

data class Person(val name: String) {
    // 직렬화 하는 로직을 동반객체에 구현
    companion object : JSONFactory<Person>{
        override fun fromJSON(jsonText: String) : Person = Person(jsonText)
//        fun<T> loadFromJSON(factory: JSONFactory<T>) : T {
//
//        }
    }
    object NameComparator : Comparator<Person> {
        override fun compare(p1: Person, p2: Person): Int {
            return p1.name.compareTo(p2.name)
        }
    }
}

// 팩토리 패턴
open class UUser private constructor(val nickName: String) {
     companion object {
         fun newSubscribingUser(email: String) : UUser {
             return UUser(email.substringBefore('@'))
         }
     }
}



/** 동반 객체에 대한 확장 함수 정의 */
/** 비즈니스 로직 모듈 */
class Personn(val firstName: String, val lastName: String) {
    companion object {
        // 동반 객체에 대한 확장 함수를 작성하려면 꼭 companion object를 선언 해야 함.-
    }
}

/** 클라이언트 서버 통신 모듈이라고 가정
 * 이런식으로 관심사를 분리하여 확장 함수 형태로 작성 가능 */
fun Personn.Companion.fromJSON(json: String): Personn {
    // 확장함수 형태로 직렬화된 json객체를 받으면 역직렬화를 통해 Person객체를 return
    return Personn("asdf", "sdfsdf")
}
//val p = Personn.fromJSON(json) // 이런식으로 호출 가능





