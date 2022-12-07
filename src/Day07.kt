private const val COMMAND_CHANGE_DIR = "$ cd "
private const val MOVE_BACK = ".."
private const val PREFIX_DIRECTORY = "dir "

fun main() {

    fun parseSystemFolderSizes(input: List<String>): List<Long> {
        val system = mutableMapOf<String, DirectoryInfo>()
        val fileRegex = "(\\d+) (\\D+)".toRegex()
        val fileLocation: MutableList<String> = mutableListOf()

        input.forEach { line ->
            if (line.startsWith(COMMAND_CHANGE_DIR)) {
                val directory = line.substringAfter(COMMAND_CHANGE_DIR)
                if (directory == MOVE_BACK) {
                    fileLocation.removeLast()
                } else {
                    fileLocation.add(directory)
                    system.putIfAbsent(fileLocation.toString(), DirectoryInfo())
                }
            } else if (line.matches(fileRegex)) {
                fileRegex.find(line)?.groups?.get(1)?.let {
                    system[fileLocation.toString()]!!.size += it.value.toInt()
                    system.forEach { (name, directoryInfo) ->
                        if (directoryInfo.contains.contains(fileLocation.toString())) {
                            system[name]!!.size += it.value.toInt()
                        }
                    }
                }
            } else if (line.startsWith(PREFIX_DIRECTORY)) {
                val dirName = line.substringAfter(PREFIX_DIRECTORY)
                system[fileLocation.toString()]!!.contains += (fileLocation + dirName).toString()
                system.forEach { (name, directoryInfo) ->
                    if (directoryInfo.contains.contains(fileLocation.toString())) {
                        system[name]!!.contains += (fileLocation + dirName).toString()
                    }
                }
            }
        }
        return system.values.map { it.size }
    }

    fun part1(input: List<String>) =
        parseSystemFolderSizes(input).filter { it <= 100000 }.sum()

    fun part2(input: List<String>): Long {
        val system = parseSystemFolderSizes(input)
        val occupiedSpace = system.max()
        return system.filter { it >= 30_000_000L - (70_000_000L - occupiedSpace) }.min()
    }

//     test if implementation meets criteria from the description:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

data class DirectoryInfo(
    val contains: MutableList<String> = mutableListOf(),
    var size: Long = 0,
)
