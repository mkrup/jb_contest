fun main() {


    fun part1(input: List<String>): Int {
        return input
            .map { parseLine(it) }
            .filter {
                it.rounds.all {
                    it.red <= 12 && it.green <= 13 && it.blue <= 14
                }
            }
            .sumOf {
                println(it)
                it.name
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { parseLine(it) }
            .map {
                Round(it.rounds.maxOf { r -> r.green },
                    it.rounds.maxOf { r -> r.blue },
                    it.rounds.maxOf { r -> r.red })
            }
            .map {
                it.green * it.blue * it.red
            }
            .sumOf {
//                println(it)
                it
            }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println("Test: " + part2(testInput))
//    check(part2(testInput) == 72)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}


private fun parseLine(line: String): Game {
//    println(line)
    val regex = "^Game (\\d+):(.+)".toRegex()
    val groups = regex.matchEntire(line)!!.groupValues
    val rounds = groups[2]
        .split(';')
        .map { it.trim() }
        .map { round ->
//            println("Rtext: " + round)
            Round(
                ".*?(\\d+) green.*".toRegex().matchEntire(round)?.groupValues?.get(1)?.toInt() ?: 0,
                ".*?(\\d+) blue.*".toRegex().matchEntire(round)?.groupValues?.get(1)?.toInt() ?: 0,
                ".*?(\\d+) red.*".toRegex().matchEntire(round)?.groupValues?.get(1)?.toInt() ?: 0
            ).also {
//                println("R: " + it)
            }
        }
    return Game(groups[1].toInt(), rounds)
}

data class Game(val name: Int, val rounds: List<Round>) {
    fun total(): Round {
        var res = Round(0, 0, 0)
        rounds.forEach { res += it }
        return res
    }
}

data class Round(val green: Int, val blue: Int, val red: Int) {
    operator fun plus(other: Round): Round =
        Round(this.green + other.green, this.blue + other.blue, this.red + other.red)
}
