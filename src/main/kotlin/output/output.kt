package output

// Text
const val TEXT_RESET = "\u001B[0m"
const val TEXT_BLACK = "\u001B[30m"
const val TEXT_RED = "\u001B[31m"
const val TEXT_GREEN = "\u001B[32m"
const val TEXT_YELLOW = "\u001B[33m"
const val TEXT_BLUE = "\u001B[34m"
const val TEXT_PURPLE = "\u001B[35m"
const val TEXT_CYAN = "\u001B[36m"
const val TEXT_WHITE = "\u001B[37m"

// Background
const val BLACK_BACKGROUND = "\u001b[40m" // BLACK
const val RED_BACKGROUND = "\u001b[41m" // RED
const val GREEN_BACKGROUND = "\u001b[42m" // GREEN
const val YELLOW_BACKGROUND = "\u001b[43m" // YELLOW
const val BLUE_BACKGROUND = "\u001b[44m" // BLUE
const val PURPLE_BACKGROUND = "\u001b[45m" // PURPLE
const val CYAN_BACKGROUND = "\u001b[46m" // CYAN
const val WHITE_BACKGROUND = "\u001b[47m" // WHITE

fun printResult(input1: List<String>, input2: List<String>, lcs: Array<Pair<Int, Int>>) {
    val indexLength1 = input1.size.toString().length
    val indexLength2 = input2.size.toString().length

    fun printCommonLine(i: Int, j: Int, line: String) {
        val index1 = i.toString().padStart(indexLength1)
        val index2 = j.toString().padStart(indexLength2)
        println("$index1 $index2   $line")
    }
    fun printDeletedLine(i: Int, line: String) {
        val index1 = i.toString().padStart(indexLength1)
        val index2 = " ".repeat(indexLength2)
        println(RED_BACKGROUND + TEXT_BLACK + "$index1 $index2 - $line" + TEXT_RESET)
    }
    fun printAddedLine(j: Int, line: String) {
        val index1 = " ".repeat(indexLength1)
        val index2 = j.toString().padStart(indexLength2)
        println(GREEN_BACKGROUND + TEXT_BLACK + "$index1 $index2 + $line" + TEXT_RESET)
    }

    var x = 0
    var y = 0
    for ((i, j) in lcs) {
        while (x < i) {
            // deleted line
            printDeletedLine(x + 1, input1[x])
            ++x
        }
        while (y < j) {
            // added line
            printAddedLine(y + 1, input2[y])
            ++y
        }
        // common line
        printCommonLine(x + 1, y + 1, input1[x])
        ++x
        ++y
    }
}