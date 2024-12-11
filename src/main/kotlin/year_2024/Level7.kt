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

    enum class Options2(val value: String) {
        Other(""),
        Add("+"),
        Multiply("*"),
        Concat("|");

        companion object {
            fun fromChar(operator: Char): Options2 =
                when (operator) {
                    '+' -> Add
                    '*' -> Multiply
                    '|' -> Concat
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

    private fun generateOptions2(optionsCount: Int): List<String> {
        return when (optionsCount) {
            1, 0 -> {
                emptyList()
            }

            2 -> {
                Options2.values().filter { it.value.isNotEmpty() }.map { it.value }
            }

            else -> {
                val list = generateOptions2(optionsCount - 1)
                val updatedList: MutableList<String> = mutableListOf()
                for (option in Options2.values().filter { it.value.isNotEmpty() }) {
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

    private fun calculateResult2(option: String, items: List<Long>): Long {
        var total = items.first()
        val list = items.toMutableList()
        list.removeFirst()
        option.forEachIndexed { index, operator ->
            when (Options2.fromChar(operator)) {
                Options2.Add -> total += list[index]
                Options2.Multiply -> total *= list[index]
                Options2.Concat -> total = (total.toString() + list[index].toString()).toLong()
                Options2.Other -> total
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

    private fun checkResult2(result: Long, items: List<Long>): Long {
        val options = generateOptions2(items.size)
        return if (options.any {
                calculateResult2(it, items) == result
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
        val sumOfGoodEquations = lines.sumOf { line ->
            val result = line.substringBeforeLast(":").toLong()
            val items = line.substringAfter(": ").split(" ").map { it.toLong() }
            checkResult2(result, items)
        }

        println("The sum of good equations with concat is $sumOfGoodEquations")
    }
}