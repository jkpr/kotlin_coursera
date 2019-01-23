package rationals

import java.math.BigInteger
import java.util.*


fun String.toRational(): Rational {
    val thisSplit = split("/", limit=2)
    return try {
        if (thisSplit.size == 2) {
            val numerator = BigInteger(thisSplit[0])
            val denominator = BigInteger(thisSplit[1])
            Rational(numerator, denominator)
        } else {
            Rational(BigInteger(this), BigInteger.ONE)
        }
    } catch (e: NumberFormatException) {
        throw e
    }
}


fun Int.toRational() = Rational(BigInteger.valueOf(toLong()), BigInteger.ONE)
fun Long.toRational() = Rational(BigInteger.valueOf(this), BigInteger.ONE)


infix fun Int.divBy(other: Int): Rational {
    return Rational(BigInteger.valueOf(toLong()), BigInteger.valueOf(other.toLong()))
}


infix fun Long.divBy(other: Long): Rational {
    return Rational(BigInteger.valueOf(this), BigInteger.valueOf(other))
}


infix fun BigInteger.divBy(other: BigInteger): Rational {
    return Rational(this, other)
}


class Rational(numerator: BigInteger, denominator: BigInteger = BigInteger.ONE) : Comparable<Rational> {
    val numerator: BigInteger
        get() = if (negative) -field else field
    val denominator: BigInteger
    val negative: Boolean

    init {
        if (denominator == BigInteger.ZERO) {
            throw IllegalAccessException("Rational denominator cannot be 0")
        }
        val gcd = numerator.gcd(denominator)
        this.numerator = (numerator / gcd).abs()
        this.denominator = (denominator / gcd).abs()
        negative = if (numerator == BigInteger.ZERO)
            false
        else
            (numerator < BigInteger.ZERO) xor (denominator < BigInteger.ZERO)
    }

    operator fun unaryPlus(): Rational {
        return copy()
    }

    operator fun unaryMinus(): Rational {
        return Rational(-numerator, denominator)
    }

    operator fun plus(other: Rational): Rational {
        val newNumerator = (numerator * other.denominator) + (other.numerator * denominator)
        val newDenominator = denominator * other.denominator
        return Rational(newNumerator, newDenominator)
    }

    operator fun minus(other: Rational): Rational {
        val newNumerator = (numerator * other.denominator) - (other.numerator * denominator)
        val newDenominator = denominator * other.denominator
        return Rational(newNumerator, newDenominator)
    }

    operator fun times(other: Rational): Rational {
        val newNumerator = numerator * other.numerator
        val newDenominator = denominator * other.denominator
        return Rational(newNumerator, newDenominator)
    }

    operator fun div(other: Rational): Rational {
        val newNumerator = numerator * other.denominator
        val newDenominator = denominator * other.numerator
        return Rational(newNumerator, newDenominator)
    }

    override operator fun compareTo(other: Rational): Int {
        return (numerator * other.denominator).compareTo(other.numerator * denominator)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Rational) {
            numerator == other.numerator &&
                    denominator == other.denominator &&
                    negative == other.negative
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(numerator, denominator, negative)
    }

    fun copy(): Rational {
        return Rational(numerator, denominator)
    }

    override fun toString(): String {
        return if (denominator == BigInteger.ONE)
            "$numerator"
        else
            "$numerator/$denominator"
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

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}