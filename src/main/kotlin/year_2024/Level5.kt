package year_2024

import Base2024

class Level5 : Base2024(5) {

    private val rules: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private val wrongUpdates: MutableList<List<Int>> = mutableListOf()
    override fun part1() {
        val middle: MutableList<Int> = mutableListOf()
        lines.forEach { line ->
            if (line.contains("|")) {
                val splitted = line.split("|")
                val first = splitted[0].toInt()
                val second = splitted[1].toInt()
                if (rules[first] == null) {
                    rules[first] = mutableListOf(second)
                } else {
                    rules[first]?.add(second)
                }
            } else if (line.contains(",")) {
                val updateList = line.split(",").map { split -> split.toInt() }
                val correctOrder = updateList.mapIndexed { index, outputItem ->
                    val othersFromIndex = updateList.drop(index + 1)
                    val othersToIndex = updateList.subList(0, index)
                    val shouldBeAfter = rules[outputItem]
                    if (shouldBeAfter != null && shouldBeAfter.size > 0) {
                        othersFromIndex.all { shouldBeAfter.contains(it) } &&
                                othersToIndex.all { !shouldBeAfter.contains(it) }
                    } else {
                        true
                    }
                }
                if (correctOrder.all { it }) {
                    middle.add(updateList[updateList.size / 2])
                } else {
                    wrongUpdates.add(updateList)
                }
            }
        }

        println("Middle sum is ${middle.sum()}")


    }

    override fun part2() {
        val correctUpdates: MutableList<List<Int>> = mutableListOf()
        wrongUpdates.forEach { update ->
            val wrongList = update.toMutableList()
            val correctList: MutableList<Int> = mutableListOf()
            while (wrongList.isNotEmpty()) {
                if (correctList.size == 0) {
                    for (num in wrongList) {
                        val differentList = wrongList.filter { it != num }
                        val shouldBeAfter = rules[num]
                        if (shouldBeAfter != null && differentList.all { shouldBeAfter.contains(it) }) {
                            correctList.add(num)
                            wrongList.removeAt(wrongList.indexOf(num))
                            break
                        }
                    }

                } else {
                    for (num in wrongList) {
                        val differentList = wrongList.filter { it != num }
                        val shouldBeAfter = rules[num]
                        val prevCondition = rules[correctList.last()]
                        if (shouldBeAfter != null && prevCondition != null && prevCondition.contains(num) && differentList.all {
                                shouldBeAfter.contains(it)
                            }) {
                            correctList.add(num)
                            wrongList.removeAt(wrongList.indexOf(num))
                            break
                        } else if (wrongList.size == 1 && shouldBeAfter == null && prevCondition != null && prevCondition.contains(
                                num
                            )
                        ) {
                            correctList.add(num)
                            wrongList.removeAt(wrongList.indexOf(num))
                            break
                        }
                    }
                }
            }
            correctUpdates.add(correctList)
        }

        println("After Ordering " + correctUpdates.sumOf { it[it.size / 2] })

    }
}