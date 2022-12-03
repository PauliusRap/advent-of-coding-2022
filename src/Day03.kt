private const val CHAR_CODE_OFFSET_UPPERCASE = 64
private const val CHAR_CODE_OFFSET_LOWERCASE = 96
private const val POINTS_OFFSET_UPPERCASE = 26

fun main() {

    fun getValueFromChar(char: Char) =
        if (char.isUpperCase()) {
            char.code - CHAR_CODE_OFFSET_UPPERCASE + POINTS_OFFSET_UPPERCASE
        } else {
            char.code - CHAR_CODE_OFFSET_LOWERCASE
        }

    fun getMatchingItemPoints(line: String): Int {
        val midPoint = line.length / 2
        val part1 = line.substring(0, midPoint)
        val part2 = line.substring(midPoint)

        var points = 0
        part1.forEach {
            if (part2.contains(it)) {
                points = getValueFromChar(it)
            }
        }
        return points
    }

    fun getMatchingItemPoints(lines: List<String>): Int {
        var points = 0
        lines[0].forEach {
            if (lines[1].contains(it) && lines[2].contains(it)) {
                points = getValueFromChar(it)
            }
        }
        return points
    }

    fun splitIntoGroupsOfThree(input: List<String>): List<List<String>> {
        var currentIndex = -1
        return input.mapIndexedNotNull { index, _ ->
            if (index - currentIndex == 3) {
                currentIndex = index
                listOf(input[index], input[index - 1], input[index - 2])
            } else null
        }
    }

    fun part1(input: List<String>) = input.sumOf { getMatchingItemPoints(it) }

    fun part2(input: List<String>) = splitIntoGroupsOfThree(input).sumOf { getMatchingItemPoints(it) }

    // test if implementation meets criteria from the description:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
