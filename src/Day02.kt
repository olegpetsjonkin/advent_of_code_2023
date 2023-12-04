fun main() {
    fun part1(input: List<String>): Int {
        return input.map { parse(it) }.filter {it.isValid(12, 13, 14)}.sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input.map { parse(it) }.sumOf { it.findPower() }
    }

    val input = readInput("Day02_example1")
    //println(part1(input))
    println(part2(input))

    val input2 = readInput("Day02")
    // println(part1(input2))
    println(part2(input2))
}

fun parse(text: String): Game {
    val gameIdString = text.split(':')
    val id = gameIdString[0].split(' ')[1].toInt()

    val gamesString = gameIdString[1].split(';')
    val gameConfigs = gamesString.map { conf ->
        val colors = conf.split(',')
        var red = 0
        var green = 0
        var blue = 0
        colors.forEach { color ->
            val colorData = color.trim().split(' ')
            when (colorData[1]) {
                "red" -> { red = colorData[0].toInt() }
                "green" -> { green = colorData[0].toInt() }
                "blue" -> { blue = colorData[0].toInt() }
            }
        }
        GameConfig(red, green, blue)
    }
    return Game(id, gameConfigs)
}

data class Game(val id: Int, val configs: List<GameConfig>) {
    fun isValid(r: Int, g: Int, b: Int): Boolean {
        return configs.all { it.isValid(r, g, b) }
    }

    fun findPower(): Int {
        var minRed = 0
        var minGreen = 0
        var minBlue = 0
        configs.forEach { conf ->
            if (conf.red > minRed) {
                minRed = conf.red
            }
            if (conf.green > minGreen) {
                minGreen = conf.green
            }
            if (conf.blue > minBlue) {
                minBlue = conf.blue
            }
        }
        println("id: ${id},: ${minRed} / ${minGreen} / ${minBlue}")

        return minRed * minGreen * minBlue
    }
}

data class GameConfig(val red: Int, val green: Int, val blue: Int) {
    fun isValid(r: Int, g: Int, b: Int): Boolean {
        return red <= r && green <= g && blue <= b
    }
}