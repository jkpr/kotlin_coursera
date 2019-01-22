package nicestring

fun String.isNice(): Boolean {
    val tests = listOf(
            !containsBadSubstring(this),
            containsAtLeastThreeVowels(this),
            containsDoubleLetter(this)
    )
    return tests.count { it } >= 2
}

fun containsBadSubstring(input: String): Boolean {
    val badSubstrings = listOf("bu", "ba", "be")
    return badSubstrings.any { it in input }
}

fun containsAtLeastThreeVowels(input: String): Boolean {
    val vowels = "aeiou"
    return input.count{ it in vowels } >= 3
}

fun containsDoubleLetter(input: String): Boolean {
    if (input.length < 2) return false
    val offsetInput = input.substring(1)
    val zipped = input zip offsetInput
    return zipped.any { (a, b) -> a == b }
}