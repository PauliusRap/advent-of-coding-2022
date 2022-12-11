private const val OPERATOR_NOOP = "noop"
private const val OPERATOR_ADDX = "addx "

fun main() {

    fun followProgramAndDoOnEachCycle(
        program: List<String>,
        registerValue: (Int) -> Unit,
        action: () -> Unit
    ) {
        program.forEach {
            if (it.startsWith(OPERATOR_NOOP)) {
                action()
            } else if (it.startsWith(OPERATOR_ADDX)) {
                val carriedStrength = it.substringAfter(OPERATOR_ADDX).toInt()
                repeat(2) { action() }
                registerValue(carriedStrength)
            }
        }
    }

    fun countSignalStrength(program: List<String>): Int {
        val time = System.currentTimeMillis()
        var turn = 0
        var register = 1
        var intervalStrengths = 0

        fun checkAndAddStrenghtsIfNeeded() {
            if (turn == 20 || turn % 40 - 20 == 0) intervalStrengths += (register * turn).also {
                println(
                    "Added $it on turn $turn"
                )
            }
        }

        followProgramAndDoOnEachCycle(
            program = program,
            registerValue = { register += it },
            action = {
                turn++
                checkAndAddStrenghtsIfNeeded()
            }
        )
        return intervalStrengths.also { println("Completed in ${System.currentTimeMillis() - time} ms") }
    }

    fun drawFromInstructions(program: List<String>): String {
        val time = System.currentTimeMillis()
        var turn = 0
        var register = 1
        val image = StringBuilder()
        fun drawPixel() = if (turn in register - 1..register + 1) {
            image.append("#")
        } else {
            image.append(".")
        }.also {
            if (turn == 39) {
                image.append("\n")
                turn = -1
            }
        }

        followProgramAndDoOnEachCycle(
            program = program,
            registerValue = { register += it },
            action = {
                drawPixel()
                turn++
            }
        )
        return image.toString()
            .also { println("Completed in ${System.currentTimeMillis() - time} ms") }
    }

    fun part1(input: List<String>) = countSignalStrength(input)

    fun part2(input: List<String>) = drawFromInstructions(input)

//     test if implementation meets criteria from the description:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(
        part2(testInput) ==
                "##..##..##..##..##..##..##..##..##..##..\n" +
                "###...###...###...###...###...###...###.\n" +
                "####....####....####....####....####....\n" +
                "#####.....#####.....#####.....#####.....\n" +
                "######......######......######......####\n" +
                "#######.......#######.......#######.....\n"
    )

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
