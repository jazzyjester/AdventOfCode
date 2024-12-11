package year_2024

import Base2024

class Level7 : Base2024(7) {
    enum class Options(val value: String) {
        Other(""),
        Add("+"),
        Multiply("*");

        companion object {
            fun fromChar(operator: Char): Options =
                when (operator) {
                    '+' -> Add
                    '*' -> Multiply
                    else -> Other
                }
        }
    }

    private fun generateOptions(optionsCount: Int): List<String> {
        return when (optionsCount) {
            1, 0 -> {
                emptyList()
            }

            2 -> {
                Options.values().filter { it.value.isNotEmpty() }.map { it.value }
            }

            else -> {
                val list = generateOptions(optionsCount - 1)
                val updatedList: MutableList<String> = mutableListOf()
                for (option in Options.values().filter { it.value.isNotEmpty() }) {
                    for (item in list) {
                        updatedList.add(option.value + item)
                    }
                }
                updatedList
            }
        }
    }

    private fun calculateResult(option: String, items: List<Long>): Long {
        var total = items.first()
        val list = items.toMutableList()
        list.removeFirst()
        option.forEachIndexed { index, operator ->
            when (Options.fromChar(operator)) {
                Options.Add -> total += list[index]
                Options.Multiply -> total *= list[index]
                Options.Other -> total
            }
        }

        return total
    }

    private fun checkResult(result: Long, items: List<Long>): Long {
        val options = generateOptions(items.size)
        return if (options.any {
                calculateResult(it, items) == result
            }) result else 0L
    }

    override fun part1() {
        val sumOfGoodEquations = lines.sumOf { line ->
            val result = line.substringBeforeLast(":").toLong()
            val items = line.substringAfter(": ").split(" ").map { it.toLong() }
            checkResult(result, items)
        }

        println("The sum of good equations is $sumOfGoodEquations")
    }

    override fun part2() {
    }
}