data class JBRange(val destStart: Long, val sourceStart: Long, val rangeLength: Long)

fun main() {

    fun foo(ranges: List<JBRange>, numToFind: Long): Long {
        val range = ranges
            .singleOrNull { numToFind in it.sourceStart..(it.sourceStart + it.rangeLength - 1) }
        return if (range == null) {
            numToFind
        } else {
            range.destStart + numToFind - range.sourceStart
        }
    }

    fun part1(input: List<String>): Long {
        val maps: Map<String, MutableList<JBRange>> = linkedMapOf(
            "seed-to-soil map:" to mutableListOf(),
            "soil-to-fertilizer map:" to mutableListOf(),
            "fertilizer-to-water map:" to mutableListOf(),
            "water-to-light map:" to mutableListOf(),
            "light-to-temperature map:" to mutableListOf(),
            "temperature-to-humidity map:" to mutableListOf(),
            "humidity-to-location map:" to mutableListOf(),
        )

        val seeds = input[0].substring(7)
            .split(' ')
            .filter { it != "" }
            .map { it.toLong() }

        var curMapKey: String? = null
        input.drop(2).forEach { line ->
            if (line == "") return@forEach

            if (maps.contains(line)) {
                curMapKey = line
                return@forEach
            }

            val curMap = maps.getValue(curMapKey!!)
//            println(line)
            val (destRangeStart, sourceRangeStart, rangeLength) = line.split(' ')
                .filter { it != "" }
                .map { it.toLong() }
            curMap.add(JBRange(destRangeStart, sourceRangeStart, rangeLength))
        }

        return seeds.map { seed ->
            var numToFind = seed
            maps.forEach {
                numToFind = foo(it.value, numToFind)
            }
//            println(numToFind)
            seed to numToFind
        }
            .minOf {
                println("S: " + it)
                it.second
            }

    }

    fun part2(input: List<String>): Long {
        val maps: Map<String, MutableList<JBRange>> = linkedMapOf(
            "seed-to-soil map:" to mutableListOf(),
            "soil-to-fertilizer map:" to mutableListOf(),
            "fertilizer-to-water map:" to mutableListOf(),
            "water-to-light map:" to mutableListOf(),
            "light-to-temperature map:" to mutableListOf(),
            "temperature-to-humidity map:" to mutableListOf(),
            "humidity-to-location map:" to mutableListOf(),
        )

        val preSeeds = input[0].substring(7)
            .split(' ')
            .filter { it != "" }
            .map { it.toLong() }
        val seeds = mutableListOf<LongRange>()
        var i = 0
        while (i < preSeeds.size) {
            seeds.add(LongRange(preSeeds[i], preSeeds[i] + preSeeds[i + 1] - 1))
            i += 2
        }

        var curMapKey: String? = null
        input.drop(2).forEach { line ->
            if (line == "") return@forEach

            if (maps.contains(line)) {
                curMapKey = line
                return@forEach
            }

            val curMap = maps.getValue(curMapKey!!)
//            println(line)
            val (destRangeStart, sourceRangeStart, rangeLength) = line.split(' ')
                .filter { it != "" }
                .map { it.toLong() }
            curMap.add(JBRange(destRangeStart, sourceRangeStart, rangeLength))
        }

        return seeds.minOf { seedR ->
            val interimMin = seedR.minOf { seed ->
                var numToFind = seed
                maps.forEach {
                    numToFind = foo(it.value, numToFind)
                }
                numToFind
            }
            println("min interim ($seedR): " + interimMin)
            interimMin
        }
        // 174137457
        // 262615889 too high
//        return seeds.first().let { seedR ->
//            seedR.minOf { seed ->
//                var numToFind = seed
//                maps.forEach {
//                    numToFind = foo(it.key, numToFind)
//                }
//                numToFind
//            }
//        }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println("Test: " + part2(testInput))
//    check(part2(testInput) == 72)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
