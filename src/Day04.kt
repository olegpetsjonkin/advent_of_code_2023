import java.util.Collections

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { getCardValue(it) }
    }

    fun part2(input: List<String>): Int {
        return getCardsValue(input)
    }

    val input = readInput("Day04_example")
    println(part1(input))
    println(part2(input))
    println("------")
    val input2 = readInput("Day04")
    println(part1(input2))
    println(part2(input2))
}

fun getCardValue(line: String): Int {

    val allNumbers = line.split(':', '|')
    val winningNumbers = allNumbers[1].split(' ').filter { it.isNotEmpty() }.map { it.trim().toInt() }
    val myNumbers = allNumbers[2].split(' ').filter { it.isNotEmpty() }.map { it.trim().toInt() }
    val myWinningNumbersCount = myNumbers.count { winningNumbers.contains(it) }

    return if (myWinningNumbersCount == 0) {
        0
    } else {
        var ret = 1
        for(i in 1 until myWinningNumbersCount) {
            ret *= 2
        }
        ret
    }
}

fun getCardsValue(lines: List<String>): Int {

    val cardsCount = mutableMapOf<Int, Int>()

    lines.forEachIndexed { index, line ->
        val allNumbers = line.split(':', '|')
        val winningNumbers = allNumbers[1].split(' ').filter { it.isNotEmpty() }.map { it.trim().toInt() }
        val myNumbers = allNumbers[2].split(' ').filter { it.isNotEmpty() }.map { it.trim().toInt() }
        val myWinningNumbersCount = myNumbers.count { winningNumbers.contains(it) }

        val cardCount = cardsCount.getOrDefault(index, 0) + 1
        cardsCount[index] = cardCount

        for (i in 1..myWinningNumbersCount) {
            val nextCardCount = cardsCount.getOrDefault(index + i, 0) + cardCount
            cardsCount[index + i] = nextCardCount
        }
    }

    return cardsCount.values.sum()
}