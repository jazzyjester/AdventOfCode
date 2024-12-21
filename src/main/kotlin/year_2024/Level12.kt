package year_2024

import Base2024

data class Box(
    val character: Char,
    val position: Point,
    var isVisited: Boolean = false
)

fun prepareBoard(lines: List<String>): List<List<Box>> {
    val boxes = lines.mapIndexed { lineIndex, line ->
        line.mapIndexed { index, char ->
            Box(
                character = char,
                position = Point(index + 1, lineIndex + 1)
            )
        }
    }

    val finalBoxes: MutableList<MutableList<Box>> = mutableListOf()
    finalBoxes.add(mutableListOf())
    repeat(boxes[0].size + 2) {
        finalBoxes[0].add(
            Box(
                character = '!',
                position = Point(-1, -1)
            )
        )
    }
    repeat(boxes.size) { index ->
        val newBoxes: MutableList<Box> = mutableListOf()
        newBoxes.add(
            Box(
                character = '!',
                position = Point(-1, -1)
            )
        )
        newBoxes.addAll(boxes[index])
        newBoxes.add(
            Box(
                character = '!',
                position = Point(-1, -1)
            )
        )
        finalBoxes.add(newBoxes)
    }
    finalBoxes.add(mutableListOf())
    repeat(boxes[0].size + 2) {
        finalBoxes[finalBoxes.size - 1].add(
            Box(
                character = '!',
                position = Point(-1, -1)
            )
        )
    }


    return finalBoxes
}

fun findNotVisibleBox(boxes: List<List<Box>>): Box? {
    return boxes.flatten().firstOrNull { !it.isVisited && it.character != '!' }
}

enum class Positions(val position: Point) {
    UP(Point(0, -1)),
    DOWN(Point(0, 1)),
    RIGHT(Point(1, 0)),
    LEFT(Point(-1, 0)),
}

fun findPath(startBox: Box, boxes: List<List<Box>>): List<Box> {
    val localBoxes: MutableList<Box> = mutableListOf()
    startBox.isVisited = true

    Positions.values().map { it.position }.forEach { position ->
        if ((boxes[startBox.position.y + position.y][startBox.position.x + position.x].character == startBox.character) &&
            (!boxes[startBox.position.y + position.y][startBox.position.x + position.x].isVisited)
        ) {
            localBoxes.addAll(
                findPath(
                    boxes[startBox.position.y + position.y][startBox.position.x + position.x],
                    boxes
                )
            )
        }
    }

    return listOf(startBox) + localBoxes
}

data class BoxHolder(
    val box: Box,
    val holders: List<Box> // The list of around boxes that are different type
)

fun findPerimeterOfPath(path: List<Box>, boxMatrix: List<List<Box>>): Int {
    val holders: MutableList<BoxHolder> = mutableListOf()
    path.forEach {
        val list: MutableList<Box> = mutableListOf()
        Positions.values().map { it.position }.forEach { position ->
            if (it.character != boxMatrix[it.position.y + position.y][it.position.x + position.x].character) {
                list.add(boxMatrix[it.position.y + position.y][it.position.x + position.x])
            }
        }
        holders.add(BoxHolder(it, list))
    }

    return holders.sumOf { it.holders.size }
}

class Level12 : Base2024(12) {
    override fun part1() {
        val boxMatrix = prepareBoard(lines)
        val paths: MutableList<List<Box>> = mutableListOf()
        while (findNotVisibleBox(boxMatrix) != null) {
            val notVisited = findNotVisibleBox(boxMatrix)!!
            paths.add(findPath(notVisited, boxMatrix))
        }

        println(paths.size)
        val price = paths.sumOf {
            findPerimeterOfPath(it, boxMatrix) * it.size
        }

        println("The total price is $price")

    }

    override fun part2() {
    }
}