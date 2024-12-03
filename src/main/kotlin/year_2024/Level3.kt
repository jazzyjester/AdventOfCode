package year_2024

import Base2024

class Level3 : Base2024(3) {
    override fun part1() {
        val oneLiner = lines.toString()
        val reg = Regex("mul\\((\\d+),(\\d+)\\)")
        val results = reg.findAll(oneLiner)
        val sumOfMultiply = results.sumOf {
            val first = it.groupValues[1]
            val second = it.groupValues[2]
            first.toInt() * second.toInt()
        }
        println("Sum of Multiply = $sumOfMultiply")
    }

    override fun part2() {
        val oneLiner = lines.toString()
        val reg = Regex("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)")
        val results = reg.findAll(oneLiner)
        var enabled = true
        val sumOfMultiplyWithDo = results.sumOf {
            when (it.value) {
                "do()" -> {
                    enabled = true
                    0
                }

                "don't()" -> {
                    enabled = false
                    0
                }

                else -> {
                    if (enabled) {
                        val first = it.groupValues[1]
                        val second = it.groupValues[2]
                        first.toInt() * second.toInt()
                    } else {
                        0
                    }
                }

            }
        }

        println("Sum of Multiply With Do & Don't = $sumOfMultiplyWithDo")
    }
}