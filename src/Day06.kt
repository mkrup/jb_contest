private data class Race(val time: Int, val distance: Int)
private data class LRace(val time: Long, val distance: Long)

fun main() {

    fun part1(input: List<String>): Int {
        val times = input[0].substring(5).split(" ").filter { it != "" }.map { it.toInt() }
        val distances = input[1].substring(9).split(" ").filter { it != "" }.map { it.toInt() }
        println(times.joinToString())
        println(distances.joinToString())

        val races = times.mapIndexed { idx, time -> Race(time = time, distance = distances[idx]) }
        val numOfWays = mutableListOf<Int>()

        races.forEach { race ->
            var succAttempts = 0
            for (buttonTime in 0..race.time) {
                if (race.distance < buttonTime * (race.time - buttonTime)) {
                    succAttempts++
                }
            }
            numOfWays.add(succAttempts)
        }

        var res = 1
        numOfWays.forEach { res *= it }
        return res
    }

    fun part2(input: List<String>): Int {
        val time = input[0].substring(5).replace(" ", "").toLong()
        val distance = input[1].substring(9).replace(" ", "").toLong()

        val races = listOf(LRace(time = time, distance = distance))
        println(races)
        val numOfWays = mutableListOf<Int>()

        races.forEach { race ->
            var succAttempts = 0
            for (buttonTime in 0..race.time) {
                if (race.distance < buttonTime * (race.time - buttonTime)) {
                    succAttempts++
                }
            }
            numOfWays.add(succAttempts)
        }

        var res = 1
        numOfWays.forEach { res *= it }
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println("Test: " + part2(testInput))
//    check(part2(testInput) == 72)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
