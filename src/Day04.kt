fun main() {

    fun getAssignmentRange(assignment: String) = with(assignment.split("-")) {
        this[0].toInt()..this[1].toInt()
    }

    fun isAnyAssignmentCovered(input: String): Boolean {
        val assignments = input.split(",")
        val range1 = getAssignmentRange(assignments[0]).toList()
        val range2 = getAssignmentRange(assignments[1]).toList()

        return range1.containsAll(range2) || range2.containsAll(range1)
    }

    fun isAnyAssignmentOverlapped(input: String): Boolean {
        val assignments = input.split(",")
        val range1 = getAssignmentRange(assignments[0])
        val range2 = getAssignmentRange(assignments[1])

        return range1.any { range2.contains(it) }
    }

    fun part1(input: List<String>) = input.count { isAnyAssignmentCovered(it) }

    fun part2(input: List<String>) = input.count { isAnyAssignmentOverlapped(it) }

    // test if implementation meets criteria from the description:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
