package year_2022

import Base2022
import java.awt.Point
import java.lang.Exception
import kotlin.math.abs
import kotlin.math.sign

class Level9 : Base2022(9) {

    enum class Dir(val x: Int, val y: Int) {
        U(0, 1),
        D(0, -1),
        R(1, 0),
        L(-1, 0);

        companion object {
            fun findByString(value: String): Dir {
                return Dir.values().find { it.toString().lowercase() == value.lowercase() } ?: throw Exception(value)
            }
        }
    }

    private fun printArray(arr: MutableList<Pair<Int, Int>>, ch: Char = '#'): Int {
        val minX = arr.minOf { it.first }
        val minY = arr.minOf { it.second }

        val maxX = arr.maxOf { it.first }
        val maxY = arr.maxOf { it.second }

        var count = 0
        for (y in (minY..maxY).reversed()) {
            for (x in minX..maxX) {
                val contains = arr.contains(Pair(x, y))
                val printable = if (contains) {
                    count++
                    ch
                } else "."
                print(printable)
            }
            println()
        }
        return count
    }

    private fun printArrayEx(arr: List<Pair<Point, Char>>): Int {
        val arrPointsOnly = arr.map { Pair(it.first.x, it.first.y) }
        val map = arr.associate { Pair(it.first.x, it.first.y) to it.second }
        val minX = arr.minOf { it.first.x }
        val minY = arr.minOf { it.first.y }

        val maxX = arr.maxOf { it.first.x }
        val maxY = arr.maxOf { it.first.y }

        var count = 0
        for (y in (minY..maxY).reversed()) {
            for (x in minX..maxX) {
                val contains = arrPointsOnly.contains(Pair(x, y))
                val printable = if (contains) {
                    count++
                    map[Pair(x, y)]
                } else "."
                print(printable)
            }
            println()
        }
        return count
    }

    private fun getTail(headX: Int, headY: Int, tailX: Int, tailY: Int): Pair<Int, Int> {
        val xDist = headX - tailX
        val yDist = headY - tailY
        var resX = tailX
        var resY = tailY

        if (abs(xDist) > 1 && headY == tailY) {
            resX += xDist.sign
        } else if (abs(yDist) > 1 && headX == tailX) {
            resY += yDist.sign
        } else if ((abs(xDist) > 1 && headY != tailY) || (abs(yDist) > 1 && headX != tailX)) {
            resX += xDist.sign
            resY += yDist.sign
        }
        return Pair(resX, resY)
    }

    override fun part1() {
        var headX = 0
        var headY = 0
        var tailX = 0
        var tailY = 0
        val tailPositions: MutableList<Pair<Int, Int>> = mutableListOf()
        tailPositions.add(Pair(0, 0))
        lines.forEach {
            val splitted = it.split(" ")
            val dir = Dir.findByString(splitted[0])
            val count = splitted[1].toInt()

            for (step in 1..count) {
                headX += dir.x
                headY += dir.y

                val tailPair = getTail(headX, headY, tailX, tailY)
                tailX = tailPair.first
                tailY = tailPair.second

                val pair = Pair(tailX, tailY)
                if (!tailPositions.contains(pair)) {
                    tailPositions.add(pair)
                }

            }
        }

        println()
        printArray(tailPositions)
        println("Total tail positions are : ${tailPositions.size}")
    }


    companion object {
        const val TAILS = 9
    }

    data class Point(val x: Int, val y: Int)

    data class TailPoint(val point: Point, val num: String)

    override fun part2() {
        var headX = 0
        var headY = 0
        val tailPoints: MutableList<Point> = mutableListOf()
        val tailPositions: MutableList<Pair<Int, Int>> = mutableListOf()
        val tailAllPoints: MutableList<TailPoint> = mutableListOf()

        repeat(TAILS) { tailPoints.add(Point(0, 0)) }
        lines.forEach {
            val splitted = it.split(" ")
            val dir = Dir.findByString(splitted[0])
            val count = splitted[1].toInt()

            for (step in 1..count) {
                headX += dir.x
                headY += dir.y

                tailAllPoints.clear()
                repeat(TAILS) { index ->
                    var tailX = tailPoints[index].x
                    var tailY = tailPoints[index].y
                    val firstHeadX = if (index == 0) headX else tailPoints[index - 1].x
                    val firstHeadY = if (index == 0) headY else tailPoints[index - 1].y

                    val tailPair = getTail(firstHeadX, firstHeadY, tailX, tailY)

                    tailX = tailPair.first
                    tailY = tailPair.second
                    tailPoints[index] = Point(tailX, tailY)

                    if (index == 8) {
                        val pair = Pair(tailX, tailY)
                        if (!tailPositions.contains(pair)) {
                            tailPositions.add(pair)
                        }
                    }
                    tailAllPoints.add(TailPoint(Point(tailX, tailY), (index + 1).toString()))

                }

            }

            println()
            tailAllPoints.add(TailPoint(Point(headX, headY), "H"))
            val list = tailAllPoints.map { it.point to it.num.first() }.toList()
            printArrayEx(list)
            println()
        }

        println()
        printArray(tailPositions)
        println("Total tail positions are : ${tailPositions.size}")

    }
}