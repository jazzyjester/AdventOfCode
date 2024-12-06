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

    override fun part2() {
        // A is the middle, need to find all the unique positions of A
        // and check around the M & S letters
    }
}