package year_2024

import Base2024

data class Point(val x: Int, val y: Int)

data class Node(
    val value: Int,
    val position: Point,
)


private fun findPaths(position: Point, value: Int, matrix: List<String>): List<Set<Node>> {
    if (position.x < 0 || position.x > matrix[0].length - 1 ||
        position.y < 0 || position.y > matrix.size - 1 || value == 9
    ) {
        return listOf(setOf(Node(value, position)))
    } else {
        val results: MutableList<Set<Node>> = mutableListOf(setOf())
        fun addNodeToStartOfList(node: Node, list: Set<Node>): Set<Node> {
            return setOf(node, *list.toTypedArray())
        }
        // x + 1
        if (position.x + 1 <= matrix[0].length - 1 && value + 1 == matrix[position.y][position.x + 1].toString()
                .toInt()
        ) {
            val next =
                findPaths(
                    Point(position.x + 1, position.y),
                    matrix[position.y][position.x + 1].toString().toInt(), matrix
                )
            next.forEach {
                results.add(addNodeToStartOfList(Node(value, position), it))
            }

        }
        // x - 1
        if (position.x - 1 >= 0 && value + 1 == matrix[position.y][position.x - 1].toString().toInt()
        ) {
            val next =
                findPaths(
                    Point(position.x - 1, position.y),
                    matrix[position.y][position.x - 1].toString().toInt(), matrix
                )
            next.forEach {
                results.add(addNodeToStartOfList(Node(value, position), it))
            }

        }
        // y + 1
        if (position.y + 1 <= matrix.size - 1 && value + 1 == matrix[position.y + 1][position.x].toString().toInt()) {
            val next =
                findPaths(
                    Point(position.x, position.y + 1),
                    matrix[position.y + 1][position.x].toString().toInt(), matrix
                )
            next.forEach {
                results.add(addNodeToStartOfList(Node(value, position), it))
            }

        }
        // y - 1
        if (position.y - 1 >= 0 && value + 1 == matrix[position.y - 1][position.x].toString().toInt()) {
            val next =
                findPaths(
                    Point(position.x, position.y - 1),
                    matrix[position.y - 1][position.x].toString().toInt(),
                    matrix
                )
            next.forEach {
                results.add(addNodeToStartOfList(Node(value, position), it))
            }
        }
        return results
    }
}

private fun findAllTrailHeads(lines: List<String>): List<Node> {
    val results: MutableList<Node> = mutableListOf()
    lines.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { index, char ->
            if (char == '0') {
                results.add(Node(0, Point(index, lineIndex)))
            }
        }
    }

    return results
}

class Level10 : Base2024(10) {
    override fun part1() {
        val findAllTrailHeads = findAllTrailHeads(lines)
        val sumOfTrailHeads = findAllTrailHeads.sumOf { headNode ->

            val paths = findPaths(headNode.position, 0, lines)
                .filter { it.size == 10 }

            val group = paths.groupBy { it.last() }
            group.size
        }

        println("Sum of trail heads is $sumOfTrailHeads")

    }

    override fun part2() {

    }
}