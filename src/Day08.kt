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

    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun findLCM(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
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

        val cycles1 = nodes
            .map { entry ->
                var curNode: Node = entry.value
                input[0].forEach { step ->
                    curNode = when (step) {
                        'L' -> nodes.getValue(curNode.left)
                        'R' -> nodes.getValue(curNode.right)
                        else -> error("")
                    }
                }
                entry.key to curNode
            }
            .toMap()

        val cycles = nodes
            .map { entry ->
                var curNode: Node = entry.value
                var i = 0L
                val map = mutableSetOf<String>()
                do {
                    curNode = cycles1.getValue(curNode.name)
                    i++
                } while (!curNode.name.endsWith('Z') && curNode.name != entry.key && map.add(curNode.name))
                entry.key to (curNode to i)
            }
            .toMap()

        val lcm = findLCM(curNodes.map { node ->
            cycles.getValue(node.name).second
        })
//            curNodes.forEachIndexed { index, node ->
//                curNodes[index] = cycles.getValue(curNodes[index].name)
//            }
        res += input[0].length * lcm
            println("res: $res")
//        require(curNodes.all { it.name.endsWith('Z') })

//        instructions.forEach { step ->
//            if (curNodes.all { it.name.endsWith('Z') }) return res
//            curNodes.forEachIndexed { index, node ->
//                val curNode = when (step) {
//                    'L' -> nodes.getValue(node.left)
//                    'R' -> nodes.getValue(node.right)
//                    else -> error("")
//                }
//                curNodes[index] = curNode
//            }
//            res++
//            println("res: $res")
//        }

        return res
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
