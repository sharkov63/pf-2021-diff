package output

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"

fun printResult(input1: List<String>, input2: List<String>, lcs: Array<Pair<Int, Int>>) {
    var x = 0
    var y = 0
    for ((i, j) in lcs) {
        while (x < i) {
            // deleted line
            println(ANSI_RED + input1[x++] + ANSI_RESET)
        }
        while (y < j) {
            // added line
            println(ANSI_GREEN + input2[y++] + ANSI_RESET)
        }
        // common line
        println(input1[x])
        ++x
        ++y
    }
}