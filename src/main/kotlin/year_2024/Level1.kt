package year_2024

import Base2024
import kotlin.math.abs

class Level1 : Base2024(1) {
    override fun part1() {
        val (first, second) = lines.map { line ->
            line.substringBefore(" ").toInt() to
                    line.substringAfterLast(" ").toInt()
        }.unzip()


        val distanceSum = first.sorted().zip(second.sorted()).sumOf { (one, two) ->
            abs(one - two)
        }

        println("Distance Sum = $distanceSum")

    }

    override fun part2() {
        val (first, second) = lines.map { line ->
            line.substringBefore(" ").toInt() to
                    line.substringAfterLast(" ").toInt()
        }.unzip()

        val countMap = second.groupingBy { it }.eachCount()

        val secondSum = first.sumOf {
            it * countMap.getOrDefault(it, 0)
        }

        println("Similarity Score is $secondSum")
    }

}