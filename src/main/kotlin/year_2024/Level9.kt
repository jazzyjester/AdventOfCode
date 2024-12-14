package year_2024

import Base2024

sealed class Block {
    class FileBlock(val ID: Long, val length: Long) : Block()
    class EmptyBlock(val ID: Long, val length: Long) : Block()
}

data class BlockIndex(val ID: Long)

private fun createStorageList(line: String): List<Block> {
    var fileCounter = 0L
    var emptyCounter = 0L
    val results: MutableList<Block> = mutableListOf()
    line.forEachIndexed { index, char ->
        val block: Block = when (index % 2 == 0) {
            true -> {
                Block.FileBlock(fileCounter++, char.toString().toLong())
            }

            false -> {
                Block.EmptyBlock(emptyCounter++, char.toString().toLong())
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

private fun calcHash2(list: List<Block>): Long {
    val result: MutableList<BlockIndex> = mutableListOf()
    list.map { block ->
        when (block) {
            is Block.EmptyBlock -> {
                repeat((0 until block.length).count()) {
                    result.add(BlockIndex(0))
                }
            }

            is Block.FileBlock -> {
                repeat((0 until block.length).count()) {
                    result.add(BlockIndex(block.ID))
                }
            }
        }
    }

    return result.mapIndexed { index, block -> index * block.ID }.sumOf { it }
}


private fun moveFilesToEmptyAvailableSpace(data: List<Block>): List<Block> {
    val list = data.toMutableList()
    val skipped: MutableList<Block.FileBlock> = mutableListOf()
    val moved: MutableList<Block.FileBlock> = mutableListOf()
    val emptyMoved: MutableList<Block.EmptyBlock> = mutableListOf()
    while (true) {
        val lastFileBlock =
            list.filterIsInstance<Block.FileBlock>().lastOrNull { !skipped.contains(it) && !moved.contains(it) }
                ?: break
        val lastFileBlockIndex = list.indexOf(lastFileBlock)
        val firstEmptyWithGoodSize = list.take(lastFileBlockIndex).filterIsInstance<Block.EmptyBlock>().firstOrNull {
            !emptyMoved.contains(it) && it.length >= lastFileBlock.length
        }
        if (firstEmptyWithGoodSize == null) {
            // Can't find it, save it and find next
            skipped += lastFileBlock
            continue
        }
        val firstEmptyWithGoodSizeIndex = list.indexOf(firstEmptyWithGoodSize)
        if (firstEmptyWithGoodSize.length > lastFileBlock.length) {
            // There are more space left
            // Put and decrease
            list.removeAt(lastFileBlockIndex)
            val emptyBlock = Block.EmptyBlock(System.nanoTime(), lastFileBlock.length)
            list.add(lastFileBlockIndex, emptyBlock)
            emptyMoved += emptyBlock
            list.add(firstEmptyWithGoodSizeIndex, lastFileBlock)
            list[firstEmptyWithGoodSizeIndex + 1] =
                Block.EmptyBlock(
                    ID = firstEmptyWithGoodSize.ID,
                    length = firstEmptyWithGoodSize.length - lastFileBlock.length
                )
            moved += lastFileBlock
        } else {
            // Exactly the same size
            list.removeAt(lastFileBlockIndex)
            val emptyBlock = Block.EmptyBlock(System.nanoTime(), lastFileBlock.length)
            list.add(lastFileBlockIndex, emptyBlock)
            emptyMoved += emptyBlock
            list.add(firstEmptyWithGoodSizeIndex, lastFileBlock)
            list.removeAt(firstEmptyWithGoodSizeIndex + 1)
            moved += lastFileBlock
        }

    }

    return list
}

class Level9 : Base2024(9) {
    override fun part1() {
        val createStorageList = createStorageList(lines.first())
        val full = createFullStorageList(createStorageList)
        val moved = moveFilesToEmptySpaces(full)
        println("First Hash is " + calcHash(moved))
    }

    override fun part2() {
        val createStorageList = createStorageList(lines.first())
        val moved = moveFilesToEmptyAvailableSpace(createStorageList)
        println("Second Hash is " + calcHash2(moved))
    }
}