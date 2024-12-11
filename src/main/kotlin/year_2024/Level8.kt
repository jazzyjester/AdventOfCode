package year_2024

import Base2024

class Level8 : Base2024(8) {
    data class Point(val x: Int, val y: Int)
    data class Antenna(val point: Point, val char: Char)

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

    private fun calcAntinodeFromPair(pair: Pair<Point, Point>): List<Point> {
        val distB_A_X = pair.second.x - pair.first.x
        val distB_A_Y = pair.second.y - pair.first.y
        val firstNote = Point(pair.first.x + distB_A_X * 2, pair.first.y + distB_A_Y * 2)

        val distA_B_X = pair.first.x - pair.second.x
        val distA_B_Y = pair.first.y - pair.second.y
        val secondNote = Point(pair.second.x + distA_B_X * 2, pair.second.y + distA_B_Y * 2)

        return listOf(firstNote, secondNote)
    }

    private fun getAntinodes(char: Char, antennas: List<Antenna>): List<Point> {
        val walkAntennas = antennas.toMutableList()
        val totalListOfNotes: MutableList<Point> = mutableListOf()
        while (walkAntennas.size > 0) {
            val indexAntenna = walkAntennas.first()
            walkAntennas.removeAt(0)
            val pairs = walkAntennas.map { indexAntenna to it }
            val listOfPairs = pairs.map {
                calcAntinodeFromPair(it.first.point to it.second.point)
            }.flatten()

            val filtered = listOfPairs.filter {
                it.x >= 0 && it.x <= lines.size - 1 && it.y >= 0 && it.y <= lines.size - 1
            }

            totalListOfNotes.addAll(filtered)

        }

        return totalListOfNotes
    }

    override fun part1() {
        val antennas = getAntennas(lines)
        val groupByChar = antennas.groupBy { it.char }
        val uniqueSet = groupByChar.map { entry ->
            getAntinodes(entry.key, entry.value)
        }.flatten().toSet()

        println("The total unique set of nodes = ${uniqueSet.size}")

    }

    override fun part2() {
    }
}