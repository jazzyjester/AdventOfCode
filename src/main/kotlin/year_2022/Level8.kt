package year_2022

import Base2022

class Level8 : Base2022(8) {

    fun getColArray(col: Int, mat: MutableList<MutableList<Int>>): List<Int> {
        return (0 until mat.size).map {
            mat[it][col]
        }
    }

    fun checkPosition(row: Int, col: Int, mat: MutableList<MutableList<Int>>): Boolean {
        val value = mat[row][col]
        val rowArr = mat[row]
        val colArr = getColArray(col, mat)

        val toCol = colArr.take(row)
        val fromCol = colArr.takeLast(colArr.size - 1 - row)

        val toRow = rowArr.take(col)
        val fromRow = rowArr.takeLast(rowArr.size - 1 - col)

        return toCol.all { it < value } ||
                fromCol.all { it < value } ||
                toRow.all { it < value } ||
                fromRow.all { it < value }
    }

    fun countLessArray(value: Int, arr: List<Int>): Int {
        var counter = 0
        for (ind in arr.indices) {
            counter++
            if (arr[ind] >= value) {
                break
            }

        }
        return counter
    }

    fun countPosition(row: Int, col: Int, mat: MutableList<MutableList<Int>>): Int {
        val value = mat[row][col]
        val rowArr = mat[row]
        val colArr = getColArray(col, mat)

        val toCol = colArr.take(row)
        val fromCol = colArr.takeLast(colArr.size - 1 - row)
        val toRow = rowArr.take(col)
        val fromRow = rowArr.takeLast(rowArr.size - 1 - col)

        return countLessArray(value, toCol.reversed()) *
                countLessArray(value, fromCol) *
                countLessArray(value, toRow.reversed()) *
                countLessArray(value, fromRow)
    }


    fun countVisible(mat: MutableList<MutableList<Int>>): Int {
        val pairs = (0 until mat.size).map { col ->
            (0 until mat.size).map { row ->
                Pair(row, col)
            }
        }.flatten()

        return pairs.count { checkPosition(it.first, it.second, mat) }
    }

    fun countMaxScore(mat: MutableList<MutableList<Int>>): Int {
        val pairs = (0 until mat.size).map { col ->
            (0 until mat.size).map { row ->
                Pair(row, col)
            }
        }.flatten()

        val list: MutableList<Int> = mutableListOf()
        pairs.forEach { list.add(countPosition(it.first, it.second, mat)) }
        val sorted = list.sortedDescending()
        return sorted[0]
    }

    override fun part1() {
        val mat: MutableList<MutableList<Int>> = mutableListOf()

        lines.forEachIndexed { row, value ->
            mat.add(mutableListOf())
            value.forEachIndexed { col, char ->
                mat[row].add(col)
                mat[row][col] = char.toString().toInt()
            }
        }

        println("Visible count is  ${countVisible(mat)}")
        println("Score count is  ${countMaxScore(mat)}")
    }

    override fun part2() {

    }
}