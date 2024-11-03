package year_2022

import Base2022

class Level2 : Base2022(2) {
    enum class RPC(val value: Int, val signs: List<String>) {
        ROCK(1, listOf("X", "A")),
        PAPER(2, listOf("Y", "B")),
        SCISSORS(3, listOf("Z", "C"));

        fun checkResult(other: RPC): RPCResult {
            return if (this == other) return RPCResult.TIE
            else {
                when (this) {
                    ROCK -> if (other == PAPER) RPCResult.LOST else RPCResult.WIN
                    PAPER -> if (other == ROCK) RPCResult.WIN else RPCResult.LOST
                    SCISSORS -> if (other == ROCK) RPCResult.LOST else RPCResult.WIN
                }
            }
        }

        fun checkResult2(need: RPCNeed): RPC {
            return if (need == RPCNeed.NeedDraw) return this
            else {
                when (need) {
                    RPCNeed.NeedLose -> when (this) {
                        ROCK -> SCISSORS
                        PAPER -> ROCK
                        SCISSORS -> PAPER
                    }

                    RPCNeed.NeedWin -> when (this) {
                        ROCK -> PAPER
                        PAPER -> SCISSORS
                        SCISSORS -> ROCK
                    }

                    else -> {
                        ROCK
                    }
                }
            }
        }

        companion object {
            fun getByString(value: String): RPC {
                return values().first {
                    it.signs.contains(value)
                }
            }
        }
    }

    enum class RPCNeed(val sign: String, val points: Int) {
        NeedLose("X", 0),
        NeedDraw("Y", 3),
        NeedWin("Z", 6);

        companion object {
            fun getByString(value: String): RPCNeed {
                return values().first { it.sign == value }
            }
        }
    }

    enum class RPCResult(val winPoints: Int) {
        WIN(6),
        LOST(0),
        TIE(3)
    }

    data class Game(val elv: RPC, val me: RPC) {
        val result = me.checkResult(elv)
    }

    data class Game2(val elv: RPC, val need: RPCNeed) {
        val result = elv.checkResult2(need)
    }

    override fun part1() {
        val games = lines.map {
            val splitted = it.split(" ")
            Game(RPC.getByString(splitted[0]), RPC.getByString(splitted[1]))
        }
        val totalPoints = games.sumOf {
            it.result.winPoints + it.me.value
        }
        println("Total points : $totalPoints")
    }

    override fun part2() {
        val games2 = lines.map {
            val splitted = it.split(" ")
            Game2(RPC.getByString(splitted[0]), RPCNeed.getByString(splitted[1]))
        }
        val totalPoints2 = games2.sumOf {
            it.need.points + it.result.value
        }
        println("Total points2 : $totalPoints2")
    }
}