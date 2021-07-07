package kotlincontents.collections

/*
associtateBy, groupBy
associateBy와 groupBy함수는 지정된 키에의해 인덱싱된 콜렉션의 elements로부터 map을 만듭니다.
키는 keySelector 파라미터로 정의되며, valueSelector옵션을 적용하여 맵의 element값에 저장할 값을 정의할 수 있습니다.

associateBy와 groupBy의 차이점은 동일한 키를 포함한 오브젝트를 어떻게 처리했느냐에 따라 달라집니다.

associateBy는 마지막으로 적합한 element를 값으로써 사용합니다.
groupBy는 리스트의 모든 적합한 elements를 뷜드하고 값에 넣습니다.
 */

data class Person(val name: String, val city: String, val phone: String)     // 1

fun main() {
    val people = listOf(                                                     // 2
        Person("John", "Boston", "+1-888-123456"),
        Person("Sarah", "Munich", "+49-777-789123"),
        Person("Svyatoslav", "Saint-Petersburg", "+7-999-456789"),
        Person("Vasilisa", "Saint-Petersburg", "+7-999-123456"))

    val phoneBook = people.associateBy { it.phone }                          // 3
    val cityBook = people.associateBy(Person::phone, Person::city)           // 4
    val peopleCities = people.groupBy(Person::city, Person::name)            // 5
    val lastPersonCity = people.associateBy(Person::city, Person::name)      // 6

    println(phoneBook)
    println(cityBook)
    println(peopleCities)
    println(lastPersonCity)
}

/**
 * 1. Person data 클래스 정의
 *
 * 2. people 콜렉션 정의
 *
 * 3. 각 폰 번호에 해당하는 Person객체를 map으로 뷜드하고, valueSelector는 제공되지 않으며,
 * map의 값은 Person 오브젝트 자기 자신입니다.
 *
 * 4. owners가 살고있는 도시에 번호로부터 맵을 뷜드하고, 여기서 person::city는 valueSelector이며,
 * 해당 map의 value는 도시만을 포함하고 있습니다.
 *
 * 5. 도시를 키로 갖는 map을 뷜드하며, 맵의 값은 person이름 리스트이다.(즉, 중복된 키를 고려하여
 * 값이 List형태로 저장되는것 같습니다.
 *
 * 6. 마지막 사람이 포함된 키의 맵을 만듭니다. 이말은 머냐면 동일한 city값이 있다면 마지막 Person객체의 정보로
 * 맵이 형성된다는 말이다. 따라서 값은 각 도시에 거주하는 마지막 사람의 이름입니다.
 */