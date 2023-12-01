
fun main() {
    fun part1(input: List<String>): Int {
        return input.map(::getNumber).sum()
    }

    fun part2(input: List<String>): Int {
        return input.map(::getNumber2).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

val numbers = mapOf<CharArray, Int>(
    charArrayOf('o', 'n', 'e') to 1,
    charArrayOf('t', 'w', 'o') to 2,
    charArrayOf('t', 'h', 'r', 'e', 'e') to 3,
    charArrayOf('f', 'o', 'u', 'r') to 4,
    charArrayOf('f', 'i', 'v', 'e') to 5,
    charArrayOf('s', 'i', 'x') to 6,
    charArrayOf('s', 'e', 'v', 'e', 'n') to 7,
    charArrayOf('e', 'i', 'g', 'h', 't') to 8,
    charArrayOf('n', 'i', 'n', 'e') to 9,
)

fun getNumber2(line: String): Int {
    val chars = line.toCharArray()
    val charsSize = chars.size

    // find first
    var indexIncrement = 0
    var firstDigit: Int? = null

    while(indexIncrement < charsSize) {
        firstDigit = getInt(chars, indexIncrement, charsSize)
        if (firstDigit != null) {
            break;
        }
        indexIncrement ++
    }
    // find first
    var indexDecirment = charsSize - 1
    var lastDigit: Int? = null

    while(indexDecirment >= 0) {
        if (indexDecirment == indexIncrement) {
            lastDigit = firstDigit
            break
        }
        lastDigit = getInt(chars, indexDecirment, charsSize)
        if (lastDigit != null) {
            break;
        }
        indexDecirment --
    }

    if (lastDigit != null && firstDigit != null) {
        return firstDigit * 10 + lastDigit
    } else {
        return 0
    }
}

fun getInt(chars: CharArray, startIndex: Int, charsSize: Int): Int? {
    if (chars[startIndex].isDigit()) {
        return chars[startIndex].digitToInt()
    }
    // optimization
    if (startIndex + 3 > charsSize) {
        return null
    }
    // calculation
    var foundNumber: Int? = null
    numbers.forEach { (key, value) ->
        var found = true
        for (i in key.indices) {
            if (startIndex + i >= charsSize) {
                found = false
                break
            }
            if (key[i] != chars[startIndex + i]) {
                found = false
                break
            }
        }
        if (found) {
            foundNumber = value
            return@forEach
        }
    }
    return foundNumber
}

fun getNumber(line: String): Int {
    val digits = line.chars().filter { it.toChar().isDigit() }.map{ Character.getNumericValue(it)}.toArray()
    return digits.first() * 10 + digits.last()
}

