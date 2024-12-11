package year_2024

import Base2024

class Level8 : Base2024(8) {
    data class Point(val x: Int, val y: Int)
    data class Antenna(val point: Point, val char: Char)
    data class Antinode(val point: Point, val char: Char)

    private fun getAntennas(lines: List<String>): List<Antenna> {
        val antennas: MutableList<Antenna> = mutableListOf()
        lines.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { index, char ->
                if (char != '.') {
                    antennas += Antenna(Point(index, lineIndex), char)
                }
            }
        }

        return antennas
    }

    private fun calcAntinodeFromPair(char: Char, pair: Pair<Point, Point>): List<Antinode> {
        val distB_A_X = pair.second.x - pair.first.x
        val distB_A_Y = pair.second.y - pair.first.y
        val firstNote = Antinode(Point(pair.first.x + distB_A_X * 2, pair.first.y + distB_A_Y * 2), char)

        val distA_B_X = pair.first.x - pair.second.x
        val distA_B_Y = pair.first.y - pair.second.y
        val secondNote = Antinode(Point(pair.second.x + distA_B_X * 2, pair.second.y + distA_B_Y * 2), char)

        return listOf(firstNote, secondNote)
    }

    private fun calcAntinodeFromPair2(char: Char, pair: Pair<Point, Point>): List<Antinode> {
        var distB_A_X = pair.second.x - pair.first.x
        var distB_A_Y = pair.second.y - pair.first.y
        val listOfBA: MutableList<Antinode> = mutableListOf()
        var lastSecond = pair.first
        while (lastSecond.x + distB_A_X >= 0 && lastSecond.x + distB_A_X <= lines.size - 1 &&
            lastSecond.y + distB_A_Y >= 0 && lastSecond.y + distB_A_Y <= lines.size - 1
        ) {
            lastSecond = Point(lastSecond.x + distB_A_X, lastSecond.y + distB_A_Y)
            listOfBA += Antinode(lastSecond, char)
        }

        var distA_B_X = pair.first.x - pair.second.x
        var distA_B_Y = pair.first.y - pair.second.y
        val listOfAB: MutableList<Antinode> = mutableListOf()
        var lastFirst = pair.second
        while (lastFirst.x + distA_B_X >= 0 && lastFirst.x + distA_B_X <= lines.size - 1 &&
            lastFirst.y + distA_B_Y >= 0 && lastFirst.y + distA_B_Y <= lines.size - 1
        ) {
            lastFirst = Point(lastFirst.x + distA_B_X, lastFirst.y + distA_B_Y)
            listOfAB += Antinode(lastFirst, char)
        }
        return listOfBA + listOfAB
    }

    private fun getAntinodes(
        char: Char,
        antennas: List<Antenna>,
        getAntinodesCallback: (Char, Pair<Point, Point>) -> List<Antinode>
    ): List<Antinode> {
        val walkAntennas = antennas.toMutableList()
        val totalListOfNotes: MutableList<Antinode> = mutableListOf()
        while (walkAntennas.size > 0) {
            val indexAntenna = walkAntennas.first()
            walkAntennas.removeAt(0)
            val pairs = walkAntennas.map { indexAntenna to it }
            val listOfPairs = pairs.map {
                getAntinodesCallback(char, it.first.point to it.second.point)
            }.flatten()

            val filtered = listOfPairs.filter {
                it.point.x >= 0 && it.point.x <= lines.size - 1 && it.point.y >= 0 && it.point.y <= lines.size - 1
            }

            totalListOfNotes.addAll(filtered)

        }

        return totalListOfNotes
    }


    override fun part1() {
        val antennas = getAntennas(lines)
        val groupByChar = antennas.groupBy { it.char }
        val uniqueSet = groupByChar.map { entry ->
            getAntinodes(entry.key, entry.value) { char, pair ->
                calcAntinodeFromPair(char, pair)
            }
        }.flatten().toSet()

        println("The total unique set of nodes = ${uniqueSet.map { it.point }.toSet().size}")

    }

    private fun printMap(set: Set<Antinode>) {
        val arr = lines.map { it.toCharArray() }

        set.forEach {
            arr[it.point.y][it.point.x] = '#'
        }

        arr.forEach {
            println(it)
        }
    }

    override fun part2() {
        val antennas = getAntennas(lines)
        val groupByChar = antennas.groupBy { it.char }
        val uniqueSet = groupByChar.map { entry ->
            getAntinodes(entry.key, entry.value) { char, pair ->
                calcAntinodeFromPair2(char, pair)
            }
        }.flatten().toSet()

        println("The total unique set of nodes = ${uniqueSet.map { it.point }.toSet().size}")

    }
}