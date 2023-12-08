fun main() {

    fun part1(input: List<String>): Int {
        var instrI = 0
        val instructions = generateSequence {
            if (instrI == input[0].length) instrI = 0
            input[0][instrI++]
        }

        val nodes = input.drop(2).associate { line ->
            "(\\w{3}) = \\((\\w{3}), (\\w{3})\\)".toRegex().matchEntire(line)!!
                .groupValues
                .let {
                    it[1] to Node(it[1], it[2], it[3])
                }
        }

        var res = 0
        var curNode: Node = nodes.getValue("AAA")
        instructions.forEach { step ->
            if (curNode.name == "ZZZ") return res
            curNode = when (step) {
                'L' -> nodes.getValue(curNode.left)
                'R' -> nodes.getValue(curNode.right)
                else -> error("")
            }
            res++
        }

        return -1
    }

    fun part2(input: List<String>): Long {
        var instrI = 0
        val instructions = generateSequence {
            if (instrI == input[0].length) instrI = 0
            input[0][instrI++]
        }

        val nodes = input.drop(2).associate { line ->
            "(\\w{3}) = \\((\\w{3}), (\\w{3})\\)".toRegex().matchEntire(line)!!
                .groupValues
                .let {
                    it[1] to Node(it[1], it[2], it[3])
                }
        }

        var res = 0L
        val curNodes = nodes.filterKeys { it.endsWith('A') }.values.toMutableList()
        instructions.forEach { step ->
            if (curNodes.all { it.name.endsWith('Z') }) return res
            curNodes.forEachIndexed { index, node ->
                val curNode = when (step) {
                    'L' -> nodes.getValue(node.left)
                    'R' -> nodes.getValue(node.right)
                    else -> error("")
                }
                curNodes[index] = curNode
            }
            res++
            println("res: $res")
        }

        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    println("Test: " + part2(testInput))
//    check(part2(testInput) == 72)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

private data class Node(val name: String, val left: String, val right: String)
