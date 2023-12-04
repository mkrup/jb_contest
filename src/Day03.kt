import kotlin.math.max
import kotlin.math.min

fun main() {

    data class JBNumber(val value: Int, val row: Int, val colStart: Int, val colEnd: Int)
    data class JBNumber2(
        val value: Int,
        val row: Int,
        val colStart: Int,
        val colEnd: Int,
        val area: Set<Pair<Int, Int>>
    )

    fun getMap(input: List<String>): List<List<Char>> {
        val map = mutableListOf<MutableList<Char>>()
        input.forEachIndexed { idx, line ->
            val row = map.getOrElse(idx) { mutableListOf() }
            line.forEach {
                row.add(it)
            }

            if (map.getOrNull(idx) == null) {
                map.add(row)
            } else {
                map[idx] = row
            }
        }

        return map
    }

    fun part1(input: List<String>): Int {
        val map = getMap(input)

        val numbers = mutableListOf<JBNumber>()
        input.forEachIndexed { idx, line ->
            var i = 0
            val curNumVal = mutableListOf<Int>()
            var start: Int? = null

            while (i < line.length) {
                val cur = line[i]
                if (cur.isDigit()) {
                    curNumVal.add(cur.digitToInt())
                    if (start == null) start = i
                } else {
                    if (start != null) {
                        numbers.add(JBNumber(curNumVal.joinToString(separator = "").toInt(), idx, start, i - 1))
                        curNumVal.clear()
                        start = null
                    }
                }
                i++
            }
        }

        fun check(ch: Char) = ch != '.' && !ch.isDigit()
        return numbers
            .filter { number ->
                val a1 = check(map[number.row][max(0, number.colStart - 1)]) ||
                        check(map[number.row][min(map[number.row].size - 1, number.colEnd + 1)])

                var a = false
                for (j in max(0, number.colStart - 1)..min(map[max(0, number.row - 1)].size - 1, number.colEnd + 1)) {
                    a = a || check(map[max(0, number.row - 1)][j])
                }

                var a2 = false
                for (j in max(0, number.colStart - 1)..min(map[min(0, number.row + 1)].size - 1, number.colEnd + 1)) {
                    a2 = a2 || check(map[min(map.size - 1, number.row + 1)][j])
                }

                a || a1 || a2
            }
            .sumOf { it.value }

    }

    fun part2(input: List<String>): Int {
        val map = getMap(input)

        val numbers = mutableListOf<JBNumber2>()
        input.forEachIndexed { idx, line ->
            var i = 0
            val curNumVal = mutableListOf<Int>()
            var start: Int? = null

            while (i < line.length) {
                val cur = line[i]
                if (cur.isDigit()) {
                    curNumVal.add(cur.digitToInt())
                    if (start == null) start = i
                } else {
                    if (start != null) {
                        val area = mutableSetOf<Pair<Int, Int>>()
                        for (j in max(0, start!! - 1)..min(map[idx].size - 1, i)) {
                            area.add(idx to j)
                        }

                        for (j in max(0, start!! - 1)..min(map[max(0, idx - 1)].size - 1, i)) {
                            area.add(max(0, idx - 1) to j)
                        }

                        for (j in max(0, start!! - 1)..min(map[min(0, idx + 1)].size - 1, i)) {
                            area.add(min(map.size - 1, idx + 1) to j)
                        }

                        numbers.add(
                            JBNumber2(curNumVal.joinToString(separator = "").toInt(), idx, start!!, i - 1, area)

                        )
                        curNumVal.clear()
                        start = null
                    }
                }
                i++
            }
        }

        var res = 0
        map.forEachIndexed() { rowIdx, row ->
            row.forEachIndexed { columnIdx, ch ->
                if (ch == '*') {
                    val found = mutableListOf<JBNumber2>()
                    var i = 0
                    while (i < numbers.size && found.size != 2) {
                        if (numbers[i].area.contains(rowIdx to columnIdx)) {
                            found.add(numbers[i])
                        }
                        i++
                    }
                    if (found.size == 2)
                        res += found[0].value * found[1].value
                }
            }
        }

        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println("Test: " + part2(testInput))
//    check(part2(testInput) == 72)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
