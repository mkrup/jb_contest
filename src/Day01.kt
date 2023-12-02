private val strDigits = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

private val regex = "(${strDigits.keys.joinToString("|")})".toRegex()

fun main() {


    fun part1(input: List<String>): Int {
        return input.sumOf {
            "${it.first { c -> c.isDigit() }}${it.last { c -> c.isDigit() }}".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        var i = 0
        return input.sumOf {
//            println("i: " + i++)
            val dFirstIndex = it.indexOfFirst { c -> c.isDigit() }
            val strDigitMatchingRes = regex.find(it)
            val dFirst = if (strDigitMatchingRes != null) {
                if (dFirstIndex == -1 || dFirstIndex > strDigitMatchingRes.range.first) {
                    strDigits.getValue(strDigitMatchingRes.value)
                } else {
                    it[dFirstIndex].digitToInt()
                }
            } else {
                it[dFirstIndex].digitToInt()
            }

            val dLastIndex = it.indexOfLast { c -> c.isDigit() }
            val strDigitIndex = strDigits.keys.maxOf { key -> it.lastIndexOf(key) }
            val dLast = if (strDigitIndex != -1) {
                if (dLastIndex == -1 || dLastIndex < strDigitIndex) {
                    strDigits.getValue(strDigits.keys.maxBy { key -> it.lastIndexOf(key) })
                } else {
                    it[dLastIndex].digitToInt()
                }
            } else {
                it[dLastIndex].digitToInt()
            }

            "$dFirst$dLast".toInt().apply {
//                println(this)
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println("Test: " + part2(testInput))
    check(part2(testInput) == 72)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
