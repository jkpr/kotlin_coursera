package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0
    var wrongPosition = 0

    val secretIter = secret.iterator()
    val guessIter = guess.iterator()

    val secretLeftovers = mutableMapOf<Char, Int>()
    val guessLeftovers = mutableMapOf<Char, Int>()

    while (secretIter.hasNext() && guessIter.hasNext()) {
        val secretLetter = secretIter.next()
        val guessLetter = guessIter.next()
        if (secretLetter == guessLetter) {
            rightPosition += 1
        } else {
            val secretValue = secretLeftovers.getOrElse(secretLetter) { 0 }
            secretLeftovers[secretLetter] = secretValue + 1
            val guessValue = guessLeftovers.getOrElse(guessLetter) { 0 }
            guessLeftovers[guessLetter] = guessValue + 1
        }
    }

    for ((key, value) in guessLeftovers) {
        val secretValue = secretLeftovers.getOrElse(key) { 0 }
        val common = minOf(secretValue, value)
        wrongPosition += common
    }

    return Evaluation(rightPosition, wrongPosition)
}
