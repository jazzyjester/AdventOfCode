package year_2024

import Base2024
import kotlin.math.abs

class Level1 : Base2024(1) {
    override fun part1() {
        val first: MutableList<Int> = mutableListOf()
        val second: MutableList<Int> = mutableListOf()

        lines.forEach { line ->
            val splitted = line.split(" ").filter { it.isNotEmpty() }
            first.add(splitted[0].trim().toInt())
            second.add(splitted[1].trim().toInt())
        }

        first.sort()
        second.sort()

        val distanceSum = first.mapIndexed { index, firstItem ->
            abs(firstItem - second[index])
        }.sum()

        println("Distance Sum = $distanceSum")

    }

    override fun part2() {
        val first: MutableList<Int> = mutableListOf()
        val second: MutableList<Int> = mutableListOf()
        val secondMap: MutableMap<Int, Int> = mutableMapOf()

        lines.forEach { line ->
            val splitted = line.split(" ").filter { it.isNotEmpty() }
            first.add(splitted[0].trim().toInt())
            second.add(splitted[1].trim().toInt())
        }

        second.forEach { num ->
            if (secondMap[num] == null) {
                secondMap[num] = 1
            } else {
                secondMap[num] = secondMap[num]!! + 1
            }
        }

        val secondSum = first.sumOf {
            val count = secondMap[it] ?: 0
            it * count
        }

        println("Similarity Score is $secondSum")

    }

}