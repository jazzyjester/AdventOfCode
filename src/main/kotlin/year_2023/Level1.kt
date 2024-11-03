package year_2023

import Base2023

class Level1 : Base2023(1) {

    private fun calibrationLineNumber(line: String): Int {
        val firstChar = line.first { char -> char.isDigit() }
        val lastChar = line.last { char -> char.isDigit() }
        return (firstChar.toString() + lastChar.toString()).toInt()
    }

    override fun part1() {
        val total = lines.sumOf {
            calibrationLineNumber(it)
        }

        println("Sum All: $total")
    }

    enum class TextNumber(val number: Int) {
        one(1),
        two(2),
        three(3),
        four(4),
        five(5),
        six(6),
        seven(7),
        eight(8),
        nine(9);

    }

    data class Replace(
        val textNumber: TextNumber,
        val index: Int
    )

    private fun getPossibleReplaces(line: String): List<Replace> {
        val results: MutableList<Replace> = mutableListOf()
        line.mapIndexed { index, char ->
            val sub = line.substring(index)
            for (it in TextNumber.values()) {
                if (sub.indexOf(it.name) != -1 && sub.indexOf(it.name) == 0) {
                    results.add(Replace(it, index))
                    break
                }
            }
        }

        return results
    }

    private fun getCalibrationValue(line: String, replaces: List<Replace>): Int {
        val sorted = replaces.sortedBy { it.index }
        val firstChar = line.firstOrNull { char -> char.isDigit() }
        val lastChar = line.lastOrNull { char -> char.isDigit() }
        if (replaces.isEmpty()) {
            return (firstChar.toString() + lastChar.toString()).toInt()
        } else {
            val firstReplace = sorted.first()
            val lastReplace = if (replaces.size == 1) sorted.first() else sorted.last()

            var firstCharIndex = line.length
            var lastCharIndex = 0
            if (firstChar != null) {
                firstCharIndex = line.indexOf(firstChar)
            }
            if (lastChar != null) {
                lastCharIndex = line.lastIndexOf(lastChar)
            }

            val firstDigit =
                if (firstCharIndex < firstReplace.index) firstChar.toString() else firstReplace.textNumber.number.toString()
            val secondDigit =
                if (lastCharIndex > lastReplace.index) lastChar.toString() else lastReplace.textNumber.number.toString()

            return (firstDigit + secondDigit).toInt()
        }

    }

    override fun part2() {
        val total = lines.sumOf { line ->
            val cal = getCalibrationValue(line, getPossibleReplaces(line))
            cal

        }
        println("Sum All: $total")
    }
}