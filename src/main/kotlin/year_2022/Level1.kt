package year_2022

import Base2022

class Level1 : Base2022(1) {
    override fun part1() {
        var max = 0
        var currentCount = 0
        val sums = mutableListOf<Int>()
        lines.forEach {
            if (it.isEmpty()) {
                max = Math.max(max, currentCount)
                sums.add(currentCount)
                currentCount = 0
            } else {
                currentCount += it.toInt()
            }
        }

        println("Max is $max")
        val sorted = sums.sortedDescending()
        println("Top 3 are ${sorted.take(3).sum()}")
    }

    override fun part2() {

    }

}