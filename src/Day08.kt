fun main() {

    fun createGrid(input: List<String>): Array<Array<Int>> {
        val matrix = Array<Array<Int>>(input.size) { emptyArray() }
        input.forEachIndexed { index, line ->
            line.forEach {
                matrix[index] = matrix[index] + it.digitToInt()
            }
        }
        return matrix
    }

    fun countVisible(trees: Array<Array<Int>>): Int {
        val time = System.currentTimeMillis()
        var visible = 0
        trees.forEachIndexed { lineIndex, line ->
            line.forEachIndexed lineIter@{ rowIndex, tree ->
                if (lineIndex == 0 || lineIndex == trees.lastIndex || rowIndex == 0 || rowIndex == line.lastIndex) {
                    visible++
                } else {
                    (rowIndex - 1 downTo 0).firstOrNull {
                        trees[lineIndex][it] >= tree
                    } ?: run {
                        visible++
                        return@lineIter
                    }
                    (rowIndex + 1..line.lastIndex).firstOrNull {
                        trees[lineIndex][it] >= tree
                    } ?: run {
                        visible++
                        return@lineIter
                    }
                    (lineIndex - 1 downTo 0).firstOrNull {
                        trees[it][rowIndex] >= tree
                    } ?: run {
                        visible++
                        return@lineIter
                    }
                    (lineIndex + 1..trees.lastIndex).firstOrNull {
                        trees[it][rowIndex] >= tree
                    } ?: run {
                        visible++
                        return@lineIter
                    }
                }
            }
        }
        return visible.also { println("Part 1 time: ${System.currentTimeMillis() - time} ms") }
    }

    fun countScore(trees: Array<Array<Int>>): Int {
        val time = System.currentTimeMillis()
        var topScore = 0
        trees.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { rowIndex, tree ->
                if (lineIndex != 0 && lineIndex != trees.lastIndex && rowIndex != 0 && rowIndex != line.lastIndex) {
                    val left = (rowIndex - 1 downTo 0).takeWhileLastIncluded {
                        trees[lineIndex][it] < tree
                    }.size
                    val right = (rowIndex + 1..line.lastIndex).takeWhileLastIncluded {
                        trees[lineIndex][it] < tree
                    }.size
                    val top = (lineIndex - 1 downTo 0).takeWhileLastIncluded {
                        trees[it][rowIndex] < tree
                    }.size
                    val bottom = (lineIndex + 1..trees.lastIndex).takeWhileLastIncluded {
                        trees[it][rowIndex] < tree
                    }.size
                    val score = left * right * top * bottom
                    if (topScore < score) topScore = score
                }
            }
        }
        return topScore.also { println("Part 2 time: ${System.currentTimeMillis() - time} ms") }
    }

    fun part1(input: List<String>) = countVisible(createGrid(input))

    fun part2(input: List<String>) = countScore(createGrid(input))

//     test if implementation meets criteria from the description:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

inline fun <T> Iterable<T>.takeWhileLastIncluded(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        if (!predicate(item)) {
            list.add(item)
            break
        }
        list.add(item)
    }
    return list
}

