package year_2024

import Base2024


data class Stone(val value: Long)

class Level11 : Base2024(11) {
    override fun part1() {

        var initStones = lines[0].split(" ").map { it.toLong() }

        repeat(25) {
            val result: MutableList<Long> = mutableListOf()

            initStones.forEach { stone ->
                if (stone == 0L) {
                    result.add(1)
                } else if (stone.toString().length % 2 == 0) {
                    val length = stone.toString().length
                    result.add(stone.toString().take(length / 2).toLong())
                    result.add(stone.toString().takeLast(length / 2).toLong())
                } else {
                    result.add(stone * 2024)
                }

            }

            initStones = result
        }

        println("Total stones is ${initStones.size}")

    }

    override fun part2() {

    }
}