package year_2024

import Base2024

data class Box(
    val character: Char,
    val position: Point,
    var isVisited: Boolean = false
)

data class BoxWithCorner(
    val box: Box,
    val corner: Corners
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
                position = Point(it, 0)
            )
        )
    }
    repeat(boxes.size) { index ->
        val newBoxes: MutableList<Box> = mutableListOf()
        newBoxes.add(
            Box(
                character = '!',
                position = Point(0, index + 1)
            )
        )
        newBoxes.addAll(boxes[index])
        newBoxes.add(
            Box(
                character = '!',
                position = Point(newBoxes.size, index + 1)
            )
        )
        finalBoxes.add(newBoxes)
    }
    finalBoxes.add(mutableListOf())
    repeat(boxes[0].size + 2) {
        finalBoxes[finalBoxes.size - 1].add(
            Box(
                character = '!',
                position = Point(it, finalBoxes.size - 1)
            )
        )
    }


    return finalBoxes
}

fun findNotVisibleBox(boxes: List<List<Box>>): Box? {
    return boxes.flatten().firstOrNull { !it.isVisited && it.character != '!' }
}

enum class Positions(val position: Point) {
    TOP(Point(0, -1)),
    BOTTOM(Point(0, 1)),
    RIGHT(Point(1, 0)),
    LEFT(Point(-1, 0)),
}

enum class Corners(val first: Positions, val second: Positions) {
    LEFT_TOP(Positions.LEFT, Positions.TOP),
    TOP_RIGHT(Positions.RIGHT, Positions.TOP),
    RIGHT_BOTTOM(Positions.RIGHT, Positions.BOTTOM),
    BOTTOM_LEFT(Positions.LEFT, Positions.BOTTOM);

    fun is2Valid(box: Box, width: Int, height: Int): Boolean {
        val firstX = box.position.x + first.position.x
        val firstY = box.position.y + first.position.y

        val secondX = box.position.x + second.position.x
        val secondY = box.position.y + second.position.y


        return firstX >= 0 && firstX <= width - 1 &&
                secondX >= 0 && secondX <= width - 1 &&
                firstY >= 0 && firstY <= height - 1 &&
                secondY >= 0 && secondY <= height - 1
    }

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

fun findSidesOfPath(path: List<Box>, boxMatrix: List<List<Box>>): Int {
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

    val allAroundBoxes = holders.map { it.holders }.flatten().groupingBy { it }.eachCount().keys
    val pathCharacter = path[0].character
    val listOfCorners: MutableList<BoxWithCorner> = mutableListOf()
    allAroundBoxes.forEach { aroundbox ->
        val totalOuterCorners = Corners.values().filter { corner ->
            corner.is2Valid(aroundbox, boxMatrix[0].size, boxMatrix.size) &&
                    boxMatrix[aroundbox.position.y + corner.first.position.y][aroundbox.position.x + corner.first.position.x].character == pathCharacter &&
                    boxMatrix[aroundbox.position.y + corner.second.position.y][aroundbox.position.x + corner.second.position.x].character == pathCharacter &&
                    path.contains(boxMatrix[aroundbox.position.y + corner.first.position.y][aroundbox.position.x + corner.first.position.x]) &&
                    path.contains(boxMatrix[aroundbox.position.y + corner.second.position.y][aroundbox.position.x + corner.second.position.x])

        }
        listOfCorners.addAll(totalOuterCorners.map { BoxWithCorner(aroundbox, it) })

    }
    path.forEach { aroundBox ->
        val totalInnerCorners = Corners.values().filter { corner ->
            corner.is2Valid(aroundBox, boxMatrix[0].size, boxMatrix.size) &&
                    boxMatrix[aroundBox.position.y + corner.first.position.y][aroundBox.position.x + corner.first.position.x].character != pathCharacter &&
                    boxMatrix[aroundBox.position.y + corner.second.position.y][aroundBox.position.x + corner.second.position.x].character != pathCharacter

        }
        listOfCorners.addAll(totalInnerCorners.map { BoxWithCorner(aroundBox, it) })
    }

    val uniqueBoxWithCorner: MutableList<BoxWithCorner> = mutableListOf()
    if (listOfCorners.isNotEmpty()) {
        uniqueBoxWithCorner.add(listOfCorners.first())
        listOfCorners.removeFirst()
        listOfCorners.forEach { boxWithCorner ->
            if (uniqueBoxWithCorner.all { !isBoxWithCornerEquals(it, boxWithCorner) }) {
                uniqueBoxWithCorner.add(boxWithCorner)
            }
        }
    }


    return uniqueBoxWithCorner.size
}

fun isBoxWithCornerEquals(first: BoxWithCorner, second: BoxWithCorner): Boolean {
    val one = (second.box.position.x - first.box.position.x == 1 && second.box.position.y == first.box.position.y) && (
            second.corner == Corners.BOTTOM_LEFT && first.corner == Corners.RIGHT_BOTTOM ||
                    second.corner == Corners.LEFT_TOP && first.corner == Corners.TOP_RIGHT)

    val two = (first.box.position.x - second.box.position.x == 1 && second.box.position.y == first.box.position.y) && (
            first.corner == Corners.BOTTOM_LEFT && second.corner == Corners.RIGHT_BOTTOM ||
                    first.corner == Corners.LEFT_TOP && second.corner == Corners.TOP_RIGHT)

    val three =
        (second.box.position.y - first.box.position.y == 1 && second.box.position.x == first.box.position.x) && (
                second.corner == Corners.TOP_RIGHT && first.corner == Corners.RIGHT_BOTTOM ||
                        second.corner == Corners.LEFT_TOP && first.corner == Corners.BOTTOM_LEFT)
    val four =
        (first.box.position.y - second.box.position.y == 1 && second.box.position.x == first.box.position.x) && (
                first.corner == Corners.TOP_RIGHT && second.corner == Corners.RIGHT_BOTTOM ||
                        first.corner == Corners.LEFT_TOP && second.corner == Corners.BOTTOM_LEFT)

    return one || two || three || four
}

class Level12 : Base2024(12) {
    override fun part1() {
        val boxMatrix = prepareBoard(lines)
        val paths: MutableList<List<Box>> = mutableListOf()
        while (findNotVisibleBox(boxMatrix) != null) {
            val notVisited = findNotVisibleBox(boxMatrix)!!
            paths.add(findPath(notVisited, boxMatrix))
        }

        val price = paths.sumOf {
            findPerimeterOfPath(it, boxMatrix) * it.size
        }

        println("Part 1: The total price is $price")

    }

    override fun part2() {
        val boxMatrix = prepareBoard(lines)
        val paths: MutableList<List<Box>> = mutableListOf()
        while (findNotVisibleBox(boxMatrix) != null) {
            val notVisited = findNotVisibleBox(boxMatrix)!!
            paths.add(findPath(notVisited, boxMatrix))
        }

        val price = paths.sumOf {
            val sides = findSidesOfPath(it, boxMatrix)
            sides * it.size
        }

        println("Part2: The total price is $price")

    }
}