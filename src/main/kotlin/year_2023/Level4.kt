package year_2023

import Base2023
import kotlin.math.pow

class Level4 : Base2023(number = 4) {

    data class Card(
        val index: Int = 0,
        val cardNumbers: List<Int>,
        val myNumbers: List<Int>,
        val winNumbers: List<Int> = emptyList(),
        val copies: List<Card> = emptyList()
    )


    private fun getPointsFromCard(card: Card): Int {
        val size = card.winNumbers.size
        return if (size == 0) 0 else 2.0.pow(size.toDouble() - 1.0).toInt()
    }

    private fun getWinningNumbersFromCard(card: Card): Card {
        val wins = card.myNumbers.filter { card.cardNumbers.contains(it) }
        return card.copy(winNumbers = wins)
    }

    private fun lineToCard(index: Int, line: String): Card {
        val split = line.split(':')
        val splitData = split[1].split('|')
        return Card(
            index = index,
            cardNumbers = splitData[0].split(' ').filter { it.isNotEmpty() }.map { it.toInt() },
            myNumbers = splitData[1].split(' ').filter { it.isNotEmpty() }.map { it.toInt() },
        )
    }

    private fun processCard(card: Card, cards: List<Card>): Card {
        val length = card.winNumbers.size
        return card.copy(
            copies = if (length > 0) cards.subList(card.index + 1, card.index + 1 + length) else emptyList()
        )
    }

    override fun part1() {
        val cards: MutableList<Card> = mutableListOf()
        val processed:MutableList<Card> = mutableListOf()

        lines.forEachIndexed { index, line ->
            cards.add(getWinningNumbersFromCard(lineToCard(index, line)))
        }

        cards.forEach {
            val processedCard = processCard(it, cards)
            processed.add(processedCard)
        }


        println("Sum of points is $processed")

    }

    override fun part2() {
    }
}