package games.gameOfFifteen

import org.junit.Assert
import org.junit.Test

class TestGameOfFifteenInitializer {
    @Test
    fun testInitialPermutationIsNotTrivial() {
        repeat(5000) {
            val initializer = RandomGameInitializer()
            Assert.assertNotEquals("The initial permutation must not be trivial",
                    (1..15).toList(), initializer.initialPermutation)
        }
    }

    @Test
    fun testInitialPermutationIsEven() {
        repeat(5000) {
            val initializer = RandomGameInitializer()
            Assert.assertNotEquals("The initial permutation must be even",
                    isEven(initializer.initialPermutation))
        }

    }
}