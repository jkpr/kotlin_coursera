package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val zipped = secret zip guess
    val (equal, unequal) = zipped.partition { (a, b) -> a == b }
    val rightPosition = equal.size
    val unequalSecretCounts = unequal.map{ it.first }.groupingBy{ it }.eachCount()
    val unequalGuessCounts = unequal.map{ it.second }.groupingBy { it }.eachCount()
    val wrongPosition = unequalSecretCounts.entries.map { (key, value) ->
        val thisGuessCount = unequalGuessCounts.getOrDefault(key, 0)
        minOf(thisGuessCount, value)
    }.sum()
    return Evaluation(rightPosition, wrongPosition)
}
