package year_2024

import Base2024


class Level11 : Base2024(11) {
    override fun part1() {
        var initStones = lines[0].split(" ").map { it.toLong() }
        var counter = initStones.size
        repeat(25) { index ->
            val result: MutableList<Long> = mutableListOf()

            initStones.forEach { stone ->
                if (stone == 0L) {
                    result.add(1)
                } else if (stone.toString().length % 2 == 0) {
                    val length = stone.toString().length
                    result.add(stone.toString().take(length / 2).toLong())
                    result.add(stone.toString().takeLast(length / 2).toLong())
                } else {
                    result.add(stone * 2024)
                }

            }

            initStones = result
        }

        println("Total stones is ${initStones.size}")

    }

    /**
     * Not in use, but maybe I wll use it in some other question, so leave it
     */
    data class Node(
        var value: Long,
        val children: MutableList<Node> = mutableListOf()
    )

    /**
     * Not in use, but maybe I wll use it in some other question, so leave it
     */
    private fun findAllEmptyNodesCount(nodes: List<Node>): Int {
        val counter = nodes.sumOf {
            val total = if (it.children.isEmpty()) 1L else findAllEmptyNodesCount(it.children)
            total.toInt()
        }

        return counter
    }

    /**
     * Not in use, but maybe I wll use it in some other question, so leave it
     */
    private fun updateNode(stone: Node) {
        if (stone.children.isNotEmpty()) {
            stone.children.forEach { updateNode(it) }
        } else {
            if (stone.value == 0L) {
                stone.value = 1L
            } else if (stone.value.toString().length % 2 == 0) {
                val length = stone.value.toString().length
                stone.children.add(Node(stone.value.toString().take(length / 2).toLong()))
                stone.children.add(Node(stone.value.toString().takeLast(length / 2).toLong()))
            } else {
                stone.value *= 2024
            }
        }
    }

    override fun part2() {
        val initStones = lines[0].split(" ").map { it.toInt() }
        var countMap: MutableMap<Long, Long> = mutableMapOf()
        initStones.forEach {
            countMap[it.toLong()] = 1L
        }

        repeat(75) { index ->
            val updatedMap: MutableMap<Long, Long> = mutableMapOf()
            countMap.forEach { (key, value) ->
                if (key == 0L) {
                    updatedMap.merge(1, value, Long::plus)
                } else if (key.toString().length % 2 == 0) {
                    val length = key.toString().length
                    val first = key.toString().take(length / 2).toLong()
                    val second = key.toString().takeLast(length / 2).toLong()
                    updatedMap.merge(first, value, Long::plus)
                    updatedMap.merge(second, value, Long::plus)
                } else {
                    updatedMap.merge(key * 2024L, value, Long::plus)
                }
            }

            countMap = updatedMap
        }

        println("Total stones is ${countMap.values.sumOf { it }}")

    }
}