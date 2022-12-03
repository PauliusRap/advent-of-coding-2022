private const val FIRST_CHAR_INDEX = 0
private const val SECOND_CHAR_INDEX = 2
private const val ERROR_MESSAGE = "Unknown character!"

fun main() {

    fun getPoints(input: String): Int {
        val opponentsSign = input.toCharArray()[FIRST_CHAR_INDEX].getSign()
        val yourSign = input.toCharArray()[SECOND_CHAR_INDEX].getSign()

        return when (yourSign) {
            Sign.Rock -> 1
            Sign.Paper -> 2
            Sign.Scissors -> 3
        } + when {
            yourSign == Sign.Rock && opponentsSign == Sign.Scissors ||
                    yourSign == Sign.Scissors && opponentsSign == Sign.Paper ||
                    yourSign == Sign.Paper && opponentsSign == Sign.Rock -> 6
            yourSign == opponentsSign -> 3
            else -> 0
        }
    }

    fun transformToCorrectFormat(input: List<String>): List<String> {
        return input.map {
            val opponentsSign = it.toCharArray()[FIRST_CHAR_INDEX].getSign()
            val outcome = it.toCharArray()[SECOND_CHAR_INDEX]

            val char = when (outcome) {
                'X' -> when (opponentsSign) {
                    Sign.Rock -> 'C'
                    Sign.Paper -> 'A'
                    Sign.Scissors -> 'B'
                }
                'Y' -> when (opponentsSign) {
                    Sign.Rock -> 'A'
                    Sign.Paper -> 'B'
                    Sign.Scissors -> 'C'
                }
                'Z' -> when (opponentsSign) {
                    Sign.Rock -> 'B'
                    Sign.Paper -> 'C'
                    Sign.Scissors -> 'A'
                }
                else -> throw IllegalArgumentException(ERROR_MESSAGE)
            }
            it.replace(outcome, char)
        }
    }

    fun part1(input: List<String>) = input.sumOf { getPoints(it) }

    fun part2(input: List<String>) = transformToCorrectFormat(input).sumOf { getPoints(it) }

    // test if implementation meets criteria from the description:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class Sign {
    Rock, Paper, Scissors;
}

fun Char.getSign() =
    when (this) {
        'X', 'A' -> Sign.Rock
        'Y', 'B' -> Sign.Paper
        'Z', 'C' -> Sign.Scissors
        else -> throw IllegalArgumentException(ERROR_MESSAGE)
    }
