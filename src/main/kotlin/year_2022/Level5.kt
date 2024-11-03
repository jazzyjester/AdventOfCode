package year_2022

import Base2022

class Level5 : Base2022(5) {

    val map: MutableMap<Int, ArrayDeque<String>> = mutableMapOf()

    private fun init() {
        map.clear()

        (1..9).forEach {
            map[it] = ArrayDeque()
        }

        map[1]?.add("B")
        map[1]?.add("L")
        map[1]?.add("D")
        map[1]?.add("T")
        map[1]?.add("W")
        map[1]?.add("C")
        map[1]?.add("F")
        map[1]?.add("M")

        map[2]?.add("N")
        map[2]?.add("B")
        map[2]?.add("L")

        map[3]?.add("J")
        map[3]?.add("C")
        map[3]?.add("H")
        map[3]?.add("T")
        map[3]?.add("L")
        map[3]?.add("V")

        map[4]?.add("S")
        map[4]?.add("P")
        map[4]?.add("J")
        map[4]?.add("W")

        map[5]?.add("Z")
        map[5]?.add("S")
        map[5]?.add("C")
        map[5]?.add("F")
        map[5]?.add("T")
        map[5]?.add("L")
        map[5]?.add("R")

        map[6]?.add("W")
        map[6]?.add("D")
        map[6]?.add("G")
        map[6]?.add("B")
        map[6]?.add("H")
        map[6]?.add("N")
        map[6]?.add("Z")

        map[7]?.add("F")
        map[7]?.add("M")
        map[7]?.add("S")
        map[7]?.add("P")
        map[7]?.add("V")
        map[7]?.add("G")
        map[7]?.add("C")
        map[7]?.add("N")

        map[8]?.add("W")
        map[8]?.add("Q")
        map[8]?.add("R")
        map[8]?.add("J")
        map[8]?.add("F")
        map[8]?.add("V")
        map[8]?.add("C")
        map[8]?.add("Z")

        map[9]?.add("R")
        map[9]?.add("P")
        map[9]?.add("M")
        map[9]?.add("L")
        map[9]?.add("H")

        println(map)
    }

    override fun part1() {
        init()
        lines.forEach {
            val splitted = it.split(" ")
            val amount = splitted[1].toInt()
            val from = splitted[3].toInt()
            val to = splitted[5].toInt()


//             Part 1
            for (ind in 1..amount) {
                val removed = map[from]?.removeLast()
                map[to]?.add(removed.toString())
            }

        }

        printMap()

    }

    private fun printMap() {
        map.keys.forEach {
            val top = map[it]?.removeLast()
            print(top)
        }
        println()
    }

    override fun part2() {
        init()
        lines.forEach {
            val splitted = it.split(" ")
            val amount = splitted[1].toInt()
            val from = splitted[3].toInt()
            val to = splitted[5].toInt()

            // Part 2
            val temp = ArrayDeque<String>()
            for (ind in 1..amount) {
                val removed = map[from]?.removeLast()
                temp.add(removed.toString())
            }

            while (temp.isNotEmpty()) {
                val removed = temp.removeLast()
                map[to]?.add(removed)
            }
        }
        printMap()
    }
}