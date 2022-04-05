package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger
import kotlin.math.abs

class Rational(var n: BigInteger, var d: BigInteger = "1".toBigInteger()) : Comparable<Rational> {

    override fun compareTo(other: Rational): Int {
        val r1 = normalized()
        val r2 = other.normalized()
        return if (r1.d == r2.d) {
            when {
                r1.n > r2.n -> {
                    1
                }
                r1.n < r2.n -> {
                    -1
                }
                else -> {
                    0
                }
            }
        } else {
            val r1n = r1.n * r2.d
            val r2n = r2.n * r1.d
            if (r1n > r2n) {
                1
            } else {
                -1
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        val r1 = normalized()
        val r2 = (other as Rational).normalized()
        return (r1.d == r2.d && r1.n == r2.n)
    }

    override fun toString(): String {
        val one = 1
        return if (d == one.toBigInteger()) {
            "$n"
        } else {
            "${n}/${d}"
        }
    }

}

infix fun BigInteger.divBy(d: BigInteger): Rational {
    val zero = 0
    if (d == zero.toBigInteger()) {
        throw IllegalArgumentException()
    }

    return Rational(this, d).normalized()
}

infix fun Int.divBy(i: Int): Rational {

    if (i == 0) {
        throw IllegalArgumentException()
    }
    return Rational(this.toBigInteger(), i.toBigInteger()).normalized()

}

infix fun Long.divBy(l: Long): Rational {
    val zero: Long = 0
    if (l == zero) {
        throw IllegalArgumentException()
    }

    return Rational(this.toBigInteger(), l.toBigInteger()).normalized()
}

fun Rational.normalized(): Rational {
    val gCD = n.gcd(d)
    val zero = "0".toBigInteger()
    val rn = if (d < zero) -n else n
    val rd = if (d < zero) -d else d
    n = rn / gCD
    d = rd / gCD
    return this
}

operator fun Rational.plus(other: Rational): Rational {
    val r1 = normalized()
    val r2 = other.normalized()
    val r1n = r1.n * r2.d
    val r2d = r2.d * r1.d
    val r2n = r2.n * r1.d
    val nm = r1n + r2n
    return Rational(nm, r2d).normalized()
}

operator fun Rational.minus(other: Rational): Rational {

    val r1 = normalized()
    val r2 = other.normalized()
    val r1n = r1.n * r2.d
    val r2d = r2.d * r1.d
    val r2n = r2.n * r1.d
    val nm = r1n - r2n
    return Rational(nm, r2d).normalized()
}

operator fun Rational.times(other: Rational): Rational {

    val r1 = normalized()
    val r2 = other.normalized()
    val nm = r1.n * r2.n
    val dm = r1.d * r2.d
    return Rational(nm, dm).normalized()
}

operator fun Rational.div(other: Rational): Rational {
    val r1 = normalized()
    val r2 = other.normalized()
    val nm = r1.n * r2.d
    val dm = r1.d * r2.n
    return Rational(nm, dm).normalized()
}

operator fun Rational.unaryMinus(): Rational {
    val r1 = normalized()
    r1.n = -r1.n
    return r1
}


fun String.toRational(): Rational {
    val li = split("/")
    return if (li.size > 1) {
        val nm: BigInteger = li[0].toBigInteger()
        val dm: BigInteger = li[1].toBigInteger()
        (nm divBy dm).normalized()
    } else {
        Rational(li[0].toBigInteger())
    }
}


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3
    val sum: Rational = half + third
    println(5 divBy 6 == sum)


    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
}