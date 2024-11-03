package year_2022

import Base2022

class Level3 : Base2022(3) {
    fun findSameLetter(first: String, second: String): String {
        return first.first {
            second.contains(it)
        }.toString()
    }

    fun findSameLetter(first: String, second: String, third: String): String {
        return first.first {
            second.contains(it) && third.contains(it)
        }.toString()
    }

    override fun part1() {
        var sum = 0
        lines.forEach {
            val first = it.take(it.length / 2)
            val last = it.takeLast(it.length / 2)
            val result = findSameLetter(first, last)
            val resultChar = result.toCharArray()[0]
            if (resultChar in 'a'..'z') {
                sum += resultChar - 'a' + 1
            }
            if (resultChar in 'A'..'Z') {
                sum += resultChar - 'A' + 27
            }

        }

        println("Part 1: $sum")
    }

    override fun part2() {
        var sum = 0
        for (index in 0 until (lines.size / 3)) {
            val v1 = lines[3 * index]
            val v2 = lines[3 * index + 1]
            val v3 = lines[3 * index + 2]

            val result = findSameLetter(v1, v2, v3)
            val resultChar = result.toCharArray()[0]
            if (resultChar in 'a'..'z') {
                sum += resultChar - 'a' + 1
            }
            if (resultChar in 'A'..'Z') {
                sum += resultChar - 'A' + 27
            }

        }

        println("Part 2: $sum")
    }
}