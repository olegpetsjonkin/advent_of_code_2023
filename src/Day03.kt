fun main() {
    fun part1(input: List<String>): Int {
        val engine = parse(input)
        //println(engine)
        engine.findParts()
        //println(engine)
        return engine.scanParts()
    }

    fun part2(input: List<String>): Int {
        val engine = parse2(input)
        val gearsMap = engine.findParts2()
        println(gearsMap)
        return engine.scanParts2(gearsMap)
    }

    val input = readInput("Day03_example")
    //println(part1(input))
    println(part2(input))

    val input2 = readInput("Day03")
    //println(part1(input2))
    println(part2(input2))
}

fun parse2(input: List<String>): Engine {
    var id = 0
    val parts = input.map { line ->
        line.toCharArray().toList().map { ch ->
            id ++
            if (ch.isDigit()) {
                Part(id, ch.digitToInt())
            } else if (ch == '*') {
                Part(id, null, PartType.GEAR)
            } else {
                Part(id,null)
            }
        }
    }
    return Engine(parts)
}

fun parse(input: List<String>): Engine {
    val parts = input.map { line ->
        line.toCharArray().toList().map { ch ->
            if (ch.isDigit()) {
                Part(0, ch.digitToInt())
            } else if (ch == '.') {
                Part(0,null)
            } else {
                Part(0,null, PartType.SYMBOL)
            }
        }
    }
    return Engine(parts)
}

data class  Engine(val parts: List<List<Part>>) {
    fun findParts2() : Map<Part, MutableList<Part>> {
        val partToGears = mutableMapOf<Part, MutableList<Part>>()
        val size1 = parts.size
        parts.forEachIndexed { index1, parts1 ->
            val size2 = parts1.size
            parts1.forEachIndexed { index2, part ->
                if (part.type == PartType.GEAR) {
                    for(i1 in -1..1) {
                        for (i2 in -1..1) {
                            if (index1 + i1 in 0 until size1 && index2 + i2 in 0 until size2 )  {
                                val newPart =  parts[index1 + i1][index2 + i2]
                                if (newPart.type != PartType.GEAR && newPart.value != null) {
                                    partToGears.getOrPut(newPart){mutableListOf()}.add(part)
                                }
                            }
                        }
                    }
                }
            }
        }
        return partToGears
    }

    fun scanParts2(partToGear: Map<Part, MutableList<Part>>): Int {
        val gearsToNumbers = mutableMapOf<Part, MutableList<Int>>()

        parts.forEachIndexed { index1, parts1 ->

            var currentPart = 0
            val gears = mutableSetOf<Part>()

            parts1.forEachIndexed { index2, part ->
                if (part.value != null) {
                    partToGear[part]?.let {
                        gears.addAll(it)
                    }
                    currentPart = currentPart * 10 + part.value
                } else {
                    if (gears.isNotEmpty()) {
                        gears.forEach { gear ->
                            gearsToNumbers.getOrPut(gear){ mutableListOf() }.add(currentPart)
                        }
                    }
                    gears.clear()
                    currentPart = 0
                }
            }
            if (gears.isNotEmpty()) {
                gears.forEach { gear ->
                    gearsToNumbers.getOrPut(gear){ mutableListOf() }.add(currentPart)
                }
            }
        }
        println(gearsToNumbers)
        return gearsToNumbers.filter {it.value.size == 2}.map { it.value[0] * it.value[1] }.sum()
    }

    fun findParts() {
        val size1 = parts.size
        parts.forEachIndexed { index1, parts1 ->
            val size2 = parts1.size
            parts1.forEachIndexed { index2, part ->
                if (part.type == PartType.SYMBOL) {
                    for(i1 in -1..1) {
                        for (i2 in -1..1) {
                            if (index1 + i1 in 0 until size1 && index2 + i2 in 0 until size2 )  {
                                val newPart =  parts[index1 + i1][index2 + i2]
                                if (newPart.type != PartType.SYMBOL && newPart.value != null) {
                                    newPart.type = PartType.PART
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun scanParts(): Int {
        var sum = 0
        parts.forEachIndexed { index1, parts1 ->

            var currentPart = 0
            var isPart = false

            parts1.forEachIndexed { index2, part ->
                if (part.value != null) {
                    isPart = isPart || part.type == PartType.PART
                    currentPart = currentPart * 10 + part.value
                } else {
                    if (isPart) {
                        sum += currentPart
                    }
                    isPart = false
                    currentPart = 0
                }
            }
            if (isPart) {
                sum += currentPart
            }
        }
        return sum
    }
}

data class Part(val id: Int, val value:Int?, var type: PartType = PartType.NONE )

enum class PartType {
    NONE, SYMBOL, PART, GEAR
}