fun main() {

    fun detectUniqueCharsEndPosition(input: String, amount: Int): Int {
        val lastChars = mutableListOf<Char>()
        input.forEachIndexed { index, char ->
            lastChars.add(char)
            if (lastChars.toSet().size == lastChars.size && lastChars.size == amount) return index + 1
            if (lastChars.size == amount) lastChars.removeFirst()
        }
        return -1
    }

    fun part1(input: List<String>) = detectUniqueCharsEndPosition(input.first(), 4)

    fun part2(input: List<String>) = detectUniqueCharsEndPosition(input.first(), 14)

    // test if implementation meets criteria from the description:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
