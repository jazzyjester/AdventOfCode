package year_2024

import Base2024

class Level5 : Base2024(5) {
    override fun part1() {
        val rules: MutableMap<Int, MutableList<Int>> = mutableMapOf()
        val middle: MutableList<Int> = mutableListOf()
        lines.forEach { line ->
            if (line.contains("|")) {
                val splitted = line.split("|")
                val first = splitted[0].toInt()
                val second = splitted[1].toInt()
                if (rules[first] == null) {
                    rules[first] = mutableListOf(second)
                } else {
                    rules[first]?.add(second)
                }
            } else if (line.contains(",")) {
                val updateList = line.split(",").map { split -> split.toInt() }
                val correctOrder = updateList.mapIndexed { index, outputItem ->
                    val othersFromIndex = updateList.drop(index + 1)
                    val othersToIndex = updateList.subList(0, index)
                    val shouldBeAfter = rules[outputItem]
                    if (shouldBeAfter != null && shouldBeAfter.size > 0) {
                        othersFromIndex.all { shouldBeAfter.contains(it) } &&
                                othersToIndex.all { !shouldBeAfter.contains(it) }
                    } else {
                        true
                    }
                }
                if (correctOrder.all { it }) {
                    middle.add(updateList[updateList.size / 2])
                }
            }
        }

        println("Middle sum is ${middle.sum()}")


    }

    override fun part2() {
    }
}