package year_2024

import Base2024

class Level4 : Base2024(4) {
    companion object {
        const val XMAS = "XMAS"
    }

    override fun part1() {
        val regex = Regex(XMAS)
        val rightLeft = lines.sumOf { line ->
            val resultsRight = regex.findAll(line).toList()
            val resultsLeft = regex.findAll(line.reversed()).toList()
            resultsLeft.size + resultsRight.size
        }

        val upDownLines = lines.mapIndexed { lineIndex, line ->
            (line.indices).map {
                lines[it][lineIndex]
            }.joinToString("")
        }

        val upDown = upDownLines.sumOf { line ->
            val resultsDown = regex.findAll(line).toList()
            val resultsUp = regex.findAll(line.reversed()).toList()
            resultsDown.size + resultsUp.size
        }

        val diagonalsSet: MutableSet<String> = mutableSetOf()

        (0..lines.size - 1).forEach { y ->
            var value = ""
            (0..y).forEach { x ->
                value += lines[y - x][x].toString()
            }
            diagonalsSet.add(value)
        }

        (0..lines.size - 1).forEach { y ->
            var value = ""
            (lines.size - 1 downTo y).forEach { x ->
                value += lines[x][y + lines.size - 1 - x].toString()
            }
            diagonalsSet.add(value)
        }


        (0..lines.size - 1).forEach { y ->
            var value = ""
            (0..lines.size - 1 - y).forEach { x ->
                value += lines[x][y + x].toString()
            }
            diagonalsSet.add(value)
        }


        (0..lines.size - 1).forEach { y ->
            var value = ""
            (0..lines.size - 1 - y).forEach { x ->
                value += lines[y + x][x].toString()
            }
            diagonalsSet.add(value)
        }

        val validSet = diagonalsSet.filter { it.length >= XMAS.length }
        val validSetCount = validSet.sumOf { line ->
            val resultsOne = regex.findAll(line).toList()
            val resultsTwo = regex.findAll(line.reversed()).toList()
            resultsOne.size + resultsTwo.size
        }

        println("$XMAS appears ${rightLeft + upDown + validSetCount} times !")
    }

    data class Point(val x: Int, val y: Int)

    override fun part2() {

        // A is the middle, need to find all the unique positions of A
        // and check around the M & S letters
        val points: MutableSet<Point> = mutableSetOf()
        lines.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { index, char ->
                if (char == 'A') {
                    points.add(
                        Point(
                            x = index,
                            y = lineIndex
                        )
                    )
                }
            }
        }

        val updated = points.filter { point ->
            val x = point.x
            val y = point.y
            if (x - 1 >= 0 && x + 1 < lines.size && y - 1 >= 0 && y + 1 < lines.size) {
                ((lines[y - 1][x - 1] == 'M' && lines[y + 1][x + 1] == 'S') ||
                        ((lines[y - 1][x - 1] == 'S' && lines[y + 1][x + 1] == 'M')))
                        &&
                        ((lines[y - 1][x + 1] == 'M' && lines[y + 1][x - 1] == 'S') ||
                                ((lines[y - 1][x + 1] == 'S' && lines[y + 1][x - 1] == 'M')))

            } else {
                false
            }
        }

        println("X-MAS count is " + updated.size)
    }
}