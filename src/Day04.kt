import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
//            println(line)
            val groups = "Card +\\d+: (.*) \\| (.*)".toRegex().matchEntire(line)!!.groupValues
            val winNumbers = groups[1].split(' ').filter { it != "" }.map { it.trim().toInt() }
            val realNumbers = groups[2].split(' ').filter { it != "" }.map { it.trim().toInt() }
            val countOfWinnedNumbers = winNumbers.intersect(realNumbers).size
            if (countOfWinnedNumbers == 0)
                0
            else
                2.0.pow((countOfWinnedNumbers - 1).toDouble()).toInt()
        }
    }

    fun parseLineToCard(line: String): Card {
        val groups = "Card +(\\d+): (.*) \\| (.*)".toRegex().matchEntire(line)!!.groupValues
        val winNumbers = groups[2].split(' ').filter { it != "" }.map { it.trim().toInt() }
        val realNumbers = groups[3].split(' ').filter { it != "" }.map { it.trim().toInt() }
        return Card(
            groups[1].toInt(),
            List(winNumbers.intersect(realNumbers).size) { index -> groups[1].toInt() + index + 1 }
        )
    }


    fun part2(input: List<String>): Int {
        val cards = input
            .map { line ->
                parseLineToCard(line)
            }
            .associateBy { it.n }

        return cards.values.sumOf { processCard(it, cards) }

    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    println("Test: " + part2(testInput))
//    check(part2(testInput) == 72)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private data class Card(val n: Int, val winCards: List<Int>)

private fun processCard(card: Card, cards: Map<Int, Card>): Int {
    return 1 +
            card.winCards
                .map { cards.getValue(it) }
                .sumOf { processCard(it, cards) }
}
