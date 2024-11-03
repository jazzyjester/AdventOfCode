package year_2022

import Base2022

class Level4 : Base2022(4) {
    fun IntRange.contains(range: IntRange): Boolean {
        return range.all {
            this.contains(it)
        }
    }

    fun IntRange.overlap(range: IntRange): Boolean {
        return range.any {
            this.contains(it)
        }
    }

    override fun part1() {
        var count = 0
        var count2 = 0
        lines.forEach {
            val ranges = it.split(",")
            val s1 = ranges[0].split("-")
            val s2 = ranges[1].split("-")
            val r1 = s1[0].toInt()..s1[1].toInt()
            val r2 = s2[0].toInt()..s2[1].toInt()
            if (r1.contains(r2) || r2.contains(r1)) {
                count++
            }
            if (r1.overlap(r2) || r2.overlap(r1)) {
                count2++
            }
        }
        println("Total ranges contains each other : $count")
        println("Total ranges overlap each other : $count2")
    }

    override fun part2() {

    }
}