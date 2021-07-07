package chapter4

import kotlin.IllegalArgumentException

class Outer {
    val outer = 10
    val outerNested = Nested().nested
    val outerInner = Inner().inner

    class Nested {
        val nested = Outer().outer

    }

    inner class Inner {
        val inner = outer
        fun getOuter(): Outer = this@Outer
    }
}

interface Expr

class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.left) + eval(e.right)
        else ->
            throw IllegalArgumentException("unknown expression")
    }


sealed class Expr2 {
    class Num(val value: Int) : Expr2()
    class Sum(val left: Expr2, val right: Expr2) : Expr2()
    class Mul(val left: Expr2, val right: Expr2) : Expr2()
    class Divv(val left: Expr2, val right: Expr2) : Expr2()
}

fun eval2(e: Expr2) : Int =
    when (e) {
        is Expr2.Num -> e.value
        is Expr2.Sum -> eval2(e.left) + eval2(e.right)
        is Expr2.Mul -> eval2(e.left) * eval2(e.right)
        is Expr2.Divv -> {
            if (eval2(e.right) == 0) throw IllegalArgumentException("not divide zero")
            eval2(e.left) / eval2(e.right)
        }
    }

fun main() {
    // eval1 : 4 + 5 + 7
    println(eval(Sum(Num(eval(Sum(Num(4), Num(5)))), Num(7))))

    // eval2 : 3 * 5 / 3
    println(eval2(Expr2.Divv(Expr2.Num(eval2(Expr2.Mul(Expr2.Num(3), Expr2.Num(5)))), Expr2.Num(3))))

}

/*
중첩 클래스와 내부클래스 모두 바깥 클래스와의 멤버 접근이 가능하다 다만
중첩 클래스는 정적 클래스이므로 바깥 클래스 멤버 참조 시 객체를 생성해야 하며,
내부 클래스의 경우 객체를 생성하지 않아도 된다.(이는 묵시적으로 바깥 클래스를 참조하고 있다는 의미)

따라서 클래스를 직렬화 할 때 조심해야 한다. 특히 버튼 클래스가 바깥 클래스이고 내부의 클래스를
직렬화 한다고 할 때 inner class는 묵시적으로 버튼 클래스를 참조하고 있으므로
직렬화가 되지 않는다.

또한 중첩 클래스는 외부에서 멤버 접근이 되지만 내부 클래스는 되지 않는다.

 */