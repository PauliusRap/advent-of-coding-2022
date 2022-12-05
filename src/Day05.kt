private const val INDICATOR_BOX = '['

fun main() {

    fun parseBoxConfig(input: List<String>): MutableList<MutableList<Char>> {
        val boxes = input.takeWhile { it.contains(INDICATOR_BOX) }
        val output = MutableList<MutableList<Char>>(
            boxes.last().count { it == INDICATOR_BOX}
        ) { mutableListOf() }
        boxes.forEach { string ->
                (1 until string.length step 4).forEachIndexed { index, boxPosition ->
                    val content = string[boxPosition]
                    if (content.isLetter()) {
                        output[index].add(content)
                    }
                }
            }
        return output.onEach { it.reverse() }
    }

    fun parseInstructions(input: List<String>, instructionActions: (Int, Int, Int) -> Unit) {
        val instructionTemplate = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        val instructions = input.takeLastWhile { it.matches(instructionTemplate) }
        instructions.forEach {
            instructionTemplate.find(it)?.let { match ->
                val amountToMove = match.groups[1]?.value?.toInt() ?: 0
                val moveFrom = match.groups[2]?.value?.toInt()!! - 1
                val moveTo = match.groups[3]?.value?.toInt()!! - 1
                instructionActions(amountToMove, moveFrom, moveTo)
            }
        }
    }

    fun followInstructions9000(input: List<String>): List<List<Char>> {
        val boxConfig = parseBoxConfig(input)
        parseInstructions(input) { amountToMove, moveFrom, moveTo ->
                for (i in 1..amountToMove) {
                    boxConfig[moveTo].add(
                        boxConfig[moveFrom].removeAt(
                            boxConfig[moveFrom].lastIndex
                        )
                    )
                }
            }
        return boxConfig
    }

    fun followInstructions9001(input: List<String>): List<List<Char>> {
        val boxConfig = parseBoxConfig(input)
        parseInstructions(input) { amountToMove, moveFrom, moveTo ->
                val movedBoxes = boxConfig[moveFrom].takeLast(amountToMove)
            repeat(movedBoxes.size) { boxConfig[moveFrom].removeLast() }
            boxConfig[moveTo].addAll(movedBoxes)
            }
        return boxConfig
    }

    fun getTopCrates(boxConfig: List<List<Char>>): String {
        val topCrates = StringBuilder()
        boxConfig.forEach { topCrates.append(it.lastOrNull() ?: "") }
        return topCrates.toString()
    }

    fun part1(input: List<String>) = getTopCrates(followInstructions9000(input))
    fun part2(input: List<String>) = getTopCrates(followInstructions9001(input))

    // test if implementation meets criteria from the description:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
