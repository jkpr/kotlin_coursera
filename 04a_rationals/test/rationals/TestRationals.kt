package rationals

import org.junit.Assert
import org.junit.Test

class TestRationals {
    @Test
    fun test1Sum() {
        val sum: Rational = (1 divBy 2) + (1 divBy 3)
        Assert.assertEquals("Wrong result for sum", 5 divBy 6, sum)
    }

    @Test
    fun test2Difference() {
        val difference: Rational = (1 divBy 2) - (1 divBy 3)
        Assert.assertEquals("Wrong result for difference", 1 divBy 6, difference)
    }

    @Test
    fun test3Product() {
        val product: Rational = (1 divBy 2) * (1 divBy 3)
        Assert.assertEquals("Wrong result for product", 1 divBy 6, product)
    }

    @Test
    fun test4Quotient() {
        val quotient: Rational = (1 divBy 2) / (1 divBy 3)
        Assert.assertEquals("Wrong result for quotient", 3 divBy 2, quotient)
    }

    @Test
    fun test5Negation() {
        val negation: Rational = -(1 divBy 2)
        Assert.assertEquals("Wrong result for negation", -1 divBy 2, negation)
    }

    @Test
    fun test6Integer() {
        Assert.assertEquals("Wrong string representation for integer number",
            (2 divBy 1).toString(), "2")
    }

    @Test
    fun test7NormalizedForm() {
        Assert.assertEquals("Wrong normalized form for '-2 divBy 4'",
            (-2 divBy 4).toString(), "-1/2")
        Assert.assertEquals("Wrong normalized form for '117/1098'",
            "117/1098".toRational().toString(), "13/122")
    }

    @Test
    fun test8Comparison() {
        Assert.assertTrue("Wrong result for comparison", (1 divBy 2) < (2 divBy 3))
    }

    @Test
    fun test9InRange() {
        Assert.assertTrue("Wrong result for checking belonging to a range",
            (1 divBy 2) in (1 divBy 3)..(2 divBy 3))
    }

    @Test
    fun test10Long() {
        Assert.assertEquals("Wrong result for normalization of '2000000000L divBy 4000000000L'",
            2000000000L divBy 4000000000L, 1 divBy 2)
    }

    @Test
    fun test11BigInteger() {
        Assert.assertEquals("Wrong result for normalization of\n" +
            "\"912016490186296920119201192141970416029\".toBigInteger() divBy\n" +
            "\"1824032980372593840238402384283940832058\".toBigInteger()",
            "912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger(), 1 divBy 2)
    }
}