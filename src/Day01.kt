fun main() {
    fun getSortedCalorieList(input: List<String>): List<Int> {
        val list = mutableListOf<Int>()
        var individualCalories = 0
        input.forEach { line ->
            if (line.isBlank()) {
                list.add(individualCalories)
                individualCalories = 0
            } else {
                individualCalories += line.toInt()
            }
        }
        if (individualCalories != 0 ) list.add(individualCalories)
        return list.sortedDescending()
    }

    fun part1(input: List<String>): Int {
        return getSortedCalorieList(input).first()
    }

    fun part2(input: List<String>): Int {
        val list = getSortedCalorieList(input)
        return list[0] + list[1] + list[2]
    }

    // test if implementation meets criteria from the description:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
