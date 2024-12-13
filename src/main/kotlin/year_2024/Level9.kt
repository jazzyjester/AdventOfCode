package year_2024

import Base2024

sealed class Block {
    class FileBlock(val ID: Long, val length: Int) : Block()
    class EmptyBlock(val length: Int) : Block()
}

private fun createStorageList(line: String): List<Block> {
    var fileCounter = 0L
    val results: MutableList<Block> = mutableListOf()
    line.forEachIndexed { index, char ->
        val block: Block = when (index % 2 == 0) {
            true -> {
                Block.FileBlock(fileCounter++, char.toString().toInt())
            }

            false -> {
                Block.EmptyBlock(char.toString().toInt())
            }
        }
        results += block
    }
    return results
}

fun createFullStorageList(list: List<Block>): List<Block?> {
    val result: MutableList<Block?> = mutableListOf()
    list.forEach { block ->
        when (block) {
            is Block.EmptyBlock -> {
                repeat((0 until block.length).count()) {
                    result.add(null)
                }
            }

            is Block.FileBlock -> {
                repeat((0 until block.length).count()) {
                    result.add(block)
                }
            }
        }
    }

    return result
}

private fun moveFilesToEmptySpaces(data: List<Block?>): List<Block.FileBlock> {
    fun hasEmpty(data: List<Block?>) = data.any { it == null }
    val mutableData = data.toMutableList()

    while (hasEmpty(mutableData)) {
        val firstEmptyIndex = mutableData.indexOfFirst { it == null }
        val lastData = mutableData.last { it != null }
        val indexOfLastData = mutableData.indexOfLast { it == lastData }
        mutableData[firstEmptyIndex] = lastData
        mutableData.removeAt(indexOfLastData)
    }

    return mutableData.filterIsInstance<Block.FileBlock>()
}

private fun calcHash(list: List<Block.FileBlock>): Long {
    return list.mapIndexed { index, block -> index * block.ID }
        .sumOf { it }
}

class Level9 : Base2024(9) {
    override fun part1() {
        val createStorageList = createStorageList(lines.first())
        val full = createFullStorageList(createStorageList)
        val moved = moveFilesToEmptySpaces(full)
        println(calcHash(moved))
    }

    override fun part2() {
    }
}