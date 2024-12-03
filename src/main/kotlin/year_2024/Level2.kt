package year_2024

import Base2024
import java.lang.Math.abs

class Level2 : Base2024(2) {

    private fun getOffsets(list: List<Int>): List<Int> {
        val offsets: MutableList<Int> = mutableListOf()
        repeat(list.size - 1) {
            offsets.add(list[it + 1] - list[it])
        }
        return offsets
    }

    private fun checkOffsetsValid(offsets: List<Int>): Boolean {
        val allIncrease = offsets.all { it > 0 }
        val allDecrease = offsets.all { it < 0 }

        if (allIncrease || allDecrease) {
            val max = offsets.map { abs(it) }.max()
            val min = offsets.map { abs(it) }.min()

            if (min >= 1 && max <= 3) {
                return true
            }
        }

        return false
    }

    private fun checkDampener(list: List<Int>): Boolean {
        val results = List(list.size) { index ->
            val tempList = list.filterIndexed { tempIndex, _ -> index != tempIndex }
            checkOffsetsValid(getOffsets(tempList))
        }
        return results.any { it }
    }

    override fun part1() {
        val safeReports = lines.count { line ->
            checkOffsetsValid(getOffsets(line.split(" ").map { it.toInt() }))
        }

        println("Total safe reports are $safeReports")
    }

    override fun part2() {
        val safeWithDampener = lines.count { line ->
            val list = line.split(" ").map { it.toInt() }
            checkOffsetsValid(getOffsets(list)) || checkDampener(list)
        }

        println("Total Dampener safe reports are $safeWithDampener")
    }

}