package year_2024

import Base2024

class Level13 : Base2024(13) {
    data class Point(val x: Int, val y: Int)
    data class Machine(
        val a: Point,
        val b: Point,
        val prize: Point
    )

    data class MachineResult(
        val result: Point,
        val cost: Int
    )

    private fun calculatePrizeByPress(machine: Machine, aPress: Int, bPress: Int): MachineResult {
        val xa = aPress * machine.a.x
        val ya = aPress * machine.a.y
        val xb = bPress * machine.b.x
        val yb = bPress * machine.b.y

        return MachineResult(Point(xa + xb, ya + yb), aPress * 3 + bPress)
    }

    private fun findMinTokensForMachine(machine: Machine): Int {
        val validResults: MutableList<MachineResult> = mutableListOf()
        repeat(100) { aTimes ->
            repeat(100) { bTimes ->
                val result = calculatePrizeByPress(machine, aTimes, bTimes)
                if (result.result == machine.prize) {
                    validResults.add(result)
                }
            }
        }

        return if (validResults.isNotEmpty()) validResults.minOf { it.cost } else 0
    }

    override fun part1() {
        val machines: MutableList<Machine> = mutableListOf()
        repeat((lines.size + 1) / 4) { index ->
            val lineA = lines[index * 4]
            val lineB = lines[index * 4 + 1]
            val prize = lines[index * 4 + 2]

            val lineAValues = lineA.split(":")[1].split(",")
            val lineBValues = lineB.split(":")[1].split(",")
            val prizeValues = prize.split(":")[1].split(",")
            val ax = lineAValues[0].replace("X+", "").trim().toInt()
            val bx = lineBValues[0].replace("X+", "").trim().toInt()
            val px = prizeValues[0].replace("X=", "").trim().toInt()
            val ay = lineAValues[1].replace("Y+", "").trim().toInt()
            val by = lineBValues[1].replace("Y+", "").trim().toInt()
            val py = prizeValues[1].replace("Y=", "").trim().toInt()

            machines.add(
                Machine(
                    a = Point(ax, ay),
                    b = Point(bx, by),
                    prize = Point(px, py)
                )
            )
        }

        println(machines.sumOf {
            findMinTokensForMachine(it)
        })


    }

    override fun part2() {

    }
}