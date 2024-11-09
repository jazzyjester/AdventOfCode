package year_2023

import Base2023

class Level3 : Base2023(3) {
    data class Point(val x: Int, val y: Int)
    data class WordPosition(val number: String, val position: Point, val symbol: SymbolPosition = SymbolPosition())
    data class SymbolPosition(val symbol: Char = ' ', val position: Point = Point(0, 0))

    companion object {
        const val X_SIZE = 140
        const val Y_SIZE = 140
        const val SYMBOLS = "/*%@-=#&$+"
    }

    private fun printCharGrid(charGrid: MutableList<MutableList<Char>>) {
        charGrid.forEach { line ->
            println(line.toString())
        }
    }

    private fun replaceResults(results: MutableList<WordPosition>, charGrid: MutableList<MutableList<Char>>) {
        results.forEach { result ->
            result.number.forEachIndexed { index, char ->
                charGrid[result.position.y][result.position.x + index] = ' '
            }
        }
    }

    private fun isMagicNumber(charGrid: MutableList<MutableList<Char>>, word: WordPosition): Boolean {
        word.number.forEachIndexed { index, char ->
            // Check top
            if (word.position.y - 1 >= 0 && charGrid[word.position.y - 1][index + word.position.x] in SYMBOLS) {
                return true
            }
            // Check bottom
            if (word.position.y + 1 < Y_SIZE && charGrid[word.position.y + 1][index + word.position.x] in SYMBOLS) {
                return true
            }
        }

        // Check right
        if (word.position.x + word.number.length < X_SIZE && charGrid[word.position.y][word.position.x + word.number.length] in SYMBOLS) {
            return true
        }

        // Check left
        if (word.position.x - 1 >= 0 && charGrid[word.position.y][word.position.x - 1] in SYMBOLS) {
            return true
        }

        // Check edges
        val edges = listOf(
            Point(-1 + word.position.x, -1 + word.position.y), // Left Top
            Point(word.position.x + word.number.length, -1 + word.position.y), // Right Top
            Point(-1 + word.position.x, 1 + word.position.y), // Left Bottom
            Point(word.position.x + word.number.length, 1 + word.position.y) // Right Bottom
        )

        edges.forEach { edge ->
            if (edge.x in 0 until X_SIZE && edge.y in 0 until Y_SIZE) {
                if (charGrid[edge.y][edge.x] in SYMBOLS)
                    return true
            }
        }

        return false
    }

    private fun getUpdatedWord(charGrid: MutableList<MutableList<Char>>, word: WordPosition): WordPosition {
        word.number.forEachIndexed { index, char ->
            // Check top
            if (word.position.y - 1 >= 0 && charGrid[word.position.y - 1][index + word.position.x] in SYMBOLS) {
                return word.copy(
                    number = word.number,
                    position = word.position,
                    symbol = SymbolPosition(
                        symbol = charGrid[word.position.y - 1][index + word.position.x],
                        position = Point(index + word.position.x, word.position.y - 1)
                    )
                )
            }
            // Check bottom
            if (word.position.y + 1 < Y_SIZE && charGrid[word.position.y + 1][index + word.position.x] in SYMBOLS) {
                return word.copy(
                    number = word.number,
                    position = word.position,
                    symbol = SymbolPosition(
                        symbol = charGrid[word.position.y + 1][index + word.position.x],
                        position = Point(index + word.position.x, word.position.y + 1)
                    )
                )
            }
        }

        // Check right
        if (word.position.x + word.number.length < X_SIZE && charGrid[word.position.y][word.position.x + word.number.length] in SYMBOLS) {
            return word.copy(
                number = word.number,
                position = word.position,
                symbol = SymbolPosition(
                    symbol = charGrid[word.position.y][word.position.x + word.number.length],
                    position = Point(word.position.x + word.number.length, word.position.y)
                )
            )
        }

        // Check left
        if (word.position.x - 1 >= 0 && charGrid[word.position.y][word.position.x - 1] in SYMBOLS) {
            return word.copy(
                number = word.number,
                position = word.position,
                symbol = SymbolPosition(
                    symbol = charGrid[word.position.y][word.position.x - 1],
                    position = Point(word.position.x - 1, word.position.y)
                )
            )
        }

        // Check edges
        val edges = listOf(
            Point(-1 + word.position.x, -1 + word.position.y), // Left Top
            Point(word.position.x + word.number.length, -1 + word.position.y), // Right Top
            Point(-1 + word.position.x, 1 + word.position.y), // Left Bottom
            Point(word.position.x + word.number.length, 1 + word.position.y) // Right Bottom
        )

        edges.forEach { edge ->
            if (edge.x in 0 until X_SIZE && edge.y in 0 until Y_SIZE) {
                if (charGrid[edge.y][edge.x] in SYMBOLS)
                    return word.copy(
                        number = word.number,
                        position = word.position,
                        symbol = SymbolPosition(
                            symbol = charGrid[edge.y][edge.x],
                            position = Point(edge.x, edge.y)
                        )
                    )
            }
        }

        return word.copy(
            number = word.number,
            position = word.position
        )
    }

    private val results: MutableList<WordPosition> = mutableListOf()
    private val charGrid: MutableList<MutableList<Char>> = mutableListOf()

    private fun setup() {
        lines.forEachIndexed { lineNumber, line ->
            charGrid.add(line.toCharArray().toMutableList())
            val splitted = line.split(Regex("\\D")).filter { it.isNotEmpty() }
            var fromIndex = 0
            splitted.forEach { number ->
                val findFrom = line.substring(fromIndex)
                val foundedIndex = findFrom.indexOf(number, 0)
                if (foundedIndex != -1) {
                    results.add(WordPosition(number, Point(fromIndex + foundedIndex, lineNumber)))
                    fromIndex += foundedIndex + number.length
                }
            }

        }
    }

    override fun part1() {
        setup()

        val sumOfMagicNumbers = results.filter { word ->
            isMagicNumber(charGrid, word)
        }.sumOf {
            it.number.toInt()
        }

        println("Sum of magic numbers is $sumOfMagicNumbers")
    }


    override fun part2() {
        setup()
        val starResults = results.map { getUpdatedWord(charGrid, it) }.filter { it.symbol.symbol == '*' }
        val starMap: MutableMap<SymbolPosition, MutableSet<WordPosition>> = mutableMapOf()

        starResults.forEach { starWord ->
            if (starMap[starWord.symbol] == null) {
                starMap[starWord.symbol] = mutableSetOf(starWord)
            } else {
                starMap[starWord.symbol]?.add(starWord)
            }
        }

        val sumOfRations = starMap.keys.filter {
            (starMap[it]?.size == 2)
        }.sumOf {
            val set = starMap[it]!!
            set.first().number.toInt() * set.last().number.toInt()
        }

        println("The sum of all the ratios is $sumOfRations")

    }
}