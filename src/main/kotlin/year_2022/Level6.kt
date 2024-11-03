package year_2022

import Base2022

class Level6 : Base2022(6) {

    fun checkAllStringCharsDifferent(value: String): Boolean {
        val mapCount: MutableMap<Char, Int> = mutableMapOf()
        value.lowercase().forEach {
            if (mapCount[it] == null) {
                mapCount[it] = 1
            } else {
                val current = mapCount[it] ?: 1
                mapCount[it] = current + 1

            }
        }
        return mapCount.values.all { it == 1 }
    }

    override fun part1() {
        val data = lines.joinToString()
        for (ind in 1..data.length - 14) {
            val current = data.substring(ind, ind + 14)
            if (checkAllStringCharsDifferent(current)) {
                println("Found ! ${ind + 14} ")
                break
            }
        }
    }

    override fun part2() {

    }
}