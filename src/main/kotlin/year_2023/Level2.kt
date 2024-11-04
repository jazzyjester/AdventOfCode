package year_2023

import Base2023


class Level2 : Base2023(2) {
    enum class Color(val color: String, val maxPossible: Int) {
        GREEN("green", 13),
        BLUE("blue", 14),
        RED("red", 12);

        companion object {
            fun fromString(value: String): Color {
                return Color.values().first { it.color == value }
            }
        }
    }

    data class ColorPair(val color: Color, val amountOfBalls: Int)
    data class GameSet(val set: Set<ColorPair>) {
        fun isValid(): Boolean {
            return set.all { it.amountOfBalls <= it.color.maxPossible }
        }
    }

    data class Game(val index: Int, val sets: List<GameSet>) {
        fun isValid(): Boolean {
            return sets.all { it.isValid() }
        }
    }

    private fun lineToGame(line: String): Game {
        val gameSplit = line.split(":")
        val gameIndexData = gameSplit[0]
        val gameData = gameSplit[1]
        val gameIndex = gameIndexData.split(" ")[1].toInt()
        val gameSets = gameData.split(";")
        val gameSetsList = gameSets.map { set ->
            val pairs = set.split(",")
            val pairsList = pairs.map { pair ->
                val pairSplit = pair.trim().split(" ")
                val amount = pairSplit[0].toInt()
                val color = Color.fromString(pairSplit[1])
                ColorPair(color = color, amountOfBalls = amount)
            }.toSet()
            GameSet(pairsList)
        }

        return Game(gameIndex, gameSetsList)
    }

    override fun part1() {
        val sum = lines.map { lineToGame(it) }
            .filter { it.isValid() }
            .sumOf {
                it.index
            }
        println("Total sum of valid is :$sum")
    }

    override fun part2() {
        val sum = lines.sumOf { line ->
            val game = lineToGame(line)
            val blues = game.sets.map { it.set.filter { it.color == Color.BLUE } }.flatten()
            val greens = game.sets.map { it.set.filter { it.color == Color.GREEN } }.flatten()
            val reds = game.sets.map { it.set.filter { it.color == Color.RED } }.flatten()

            val maxBlues = blues.maxOf { it.amountOfBalls }
            val maxGreens = greens.maxOf { it.amountOfBalls }
            val maxReds = reds.maxOf { it.amountOfBalls }

            maxBlues * maxGreens * maxReds
        }

        println("The total of power is : $sum")
    }
}