fun main() {

    fun extractHands(input: List<String>) = input.map {
        val groups = "(.+) (\\d+)".toRegex().matchEntire(it)!!.groupValues

        Hand(
            groups[1]
                .map { c ->
                    JBCard.entries.single { cardFromEnum -> cardFromEnum.char == c }
                },
            groups[2].toInt()
        )
    }

    fun part1(input: List<String>): Int {
        val hands = extractHands(input)

        return hands
            .sortedWith(
                compareBy(
                    { detectCombWeight(it.cards) },
                    {
                        it.cards[0].weight
                    },
                    {
                        it.cards[1].weight
                    },
                    {
                        it.cards[2].weight
                    },
                    {
                        it.cards[3].weight
                    },
                    {
                        it.cards[4].weight
                    },
                )
            )
            .also { println(it) }
            .mapIndexed { index, hand ->
                hand.bid * (index + 1)
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val hands = extractHands(input)

        return hands
            .sortedWith(
                compareBy(
                    { detectCombWeight2(it.cards) },
                    {
                        it.cards[0].weight
                    },
                    {
                        it.cards[1].weight
                    },
                    {
                        it.cards[2].weight
                    },
                    {
                        it.cards[3].weight
                    },
                    {
                        it.cards[4].weight
                    },
                )
            )
            .also { println(it) }
            .mapIndexed { index, hand ->
                hand.bid * (index + 1)
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println("Test: " + part2(testInput))
//    check(part2(testInput) == 72)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private data class Hand(val cards: List<JBCard>, val bid: Int)

//private enum class JBCard(val char: Char, val weight: Int) {
//    A(char = 'A', 13),
//    K(char = 'K', 12),
//    Q(char = 'Q', 11),
//    J(char = 'J', 10),
//    T(char = 'T', 9),
//    C9(char = '9', 8),
//    C8(char = '8', 7),
//    C7(char = '7', 6),
//    C6(char = '6', 5),
//    C5(char = '5', 4),
//    C4(char = '4', 3),
//    C3(char = '3', 2),
//    C2(char = '2', 1)
//}

private enum class JBCard(val char: Char, val weight: Int) {
    A(char = 'A', 13),
    K(char = 'K', 12),
    Q(char = 'Q', 11),
    T(char = 'T', 9),
    C9(char = '9', 8),
    C8(char = '8', 7),
    C7(char = '7', 6),
    C6(char = '6', 5),
    C5(char = '5', 4),
    C4(char = '4', 3),
    C3(char = '3', 2),
    C2(char = '2', 1),
    J(char = 'J', 0)
}

private fun detectCombWeight(cards: List<JBCard>): Int {

    val duplicates = countDuplicates(cards)
    return when {
        duplicates.size == 1 && duplicates.single() == 5 -> {
            20
        }

        duplicates.size == 1 && duplicates.single() == 4 -> {
            19
        }

        duplicates.size == 2 && duplicates.toSet() == setOf(2, 3) -> {
            18
        }

        duplicates.size == 1 && duplicates.single() == 3 -> {
            17
        }

        duplicates.size == 2 && duplicates.toSet() == setOf(2) -> {
            16
        }

        duplicates.size == 1 && duplicates.single() == 2 -> {
            15
        }

        else -> {
            14
        }
    }
}

private fun detectCombWeight2(cards: List<JBCard>): Int {
    val duplicates = countDuplicates(cards.filter { it != JBCard.J })

    return when {
        duplicates.size == 1 && duplicates.single() == 5 -> {
            20
        }

        duplicates.size == 1 && duplicates.single() == 4 -> {
            listOf(19, 20)[cards.count { it == JBCard.J }]
        }

        duplicates.size == 2 && duplicates.toSet() == setOf(2, 3) -> {
            18
        }

        duplicates.size == 1 && duplicates.single() == 3 -> {
            listOf(17, 19, 20)[cards.count { it == JBCard.J }]
        }

        duplicates.size == 2 && duplicates.toSet() == setOf(2) -> {
            listOf(16, 18)[cards.count { it == JBCard.J }]
        }

        duplicates.size == 1 && duplicates.single() == 2 -> {
            listOf(15, 17, 19, 20)[cards.count { it == JBCard.J }]
        }

        else -> {
            listOf(14, 15, 17, 19, 20, 20)[cards.count { it == JBCard.J }]
        }
    }
}

private val combOfKind = listOf(14, 15, 17, 19, 20)

private fun countDuplicates(cards: List<JBCard>): List<Int> {
    val map = mutableMapOf<Char, Int>()
    cards
        .map { it.char }
        .forEach {
            map.merge(it, 1) { old, new ->
                old + 1
            }
        }

    return map.values.filter { it > 1 }
}
