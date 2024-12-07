package year_2024

import Base2024

class Level6 : Base2024(6) {
    enum class Dir(val x: Int, val y: Int) {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

        fun next(): Dir = when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }

    data class Point(val x: Int, val y: Int)

    override fun part1() {
        val points: MutableSet<Point> = mutableSetOf()
        var dir = Dir.UP
        var head = Point(-1, -1)
        for (lineIndex in lines.indices) {
            for (charIndex in lines[lineIndex].indices) {
                if (lines[lineIndex][charIndex] == '^') {
                    head = Point(charIndex, lineIndex)
                    break
                }
                if (head.x != -1 && head.y != -1) {
                    break
                }
            }
        }

        println(head)
        points.add(head)
        while (true) {
            val destination = Point(head.x + dir.x, head.y + dir.y)
            if (destination.x == -1 || destination.x == lines.size || destination.y == -1 || destination.y == lines.size) {
                break
            }
            if (lines[destination.y][destination.x] == '#') {
                // obstacle
                dir = dir.next()
            } else {
                head = destination
                points.add(head)
            }
        }

        println("Total distinct positions ${points.size}")

    }

    override fun part2() {
    }
}