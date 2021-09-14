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
const val BLACK_BACKGROUND = "\u001b[40m"
const val RED_BACKGROUND = "\u001b[41m"
const val GREEN_BACKGROUND = "\u001b[42m"
const val YELLOW_BACKGROUND = "\u001b[43m"
const val BLUE_BACKGROUND = "\u001b[44m"
const val PURPLE_BACKGROUND = "\u001b[45m"
const val CYAN_BACKGROUND = "\u001b[46m"
const val WHITE_BACKGROUND = "\u001b[47m"

enum class LineType {
    COMMON,
    DELETED,
    ADDED,
}

fun printResult(input1: List<String>, input2: List<String>, lcs: Array<Pair<Int, Int>>) {
    val indexLabelLength1 = input1.size.toString().length
    val indexLabelLength2 = input2.size.toString().length

    data class Line(val type: LineType, val index1: Int? = null, val index2: Int? = null) {
        fun getLine(): String {
            return if (index1 != null)
                input1[index1 - 1];
            else if (index2 != null)
                input2[index2 - 1];
            else
                ""
        }

        fun print() {
            val indexLabel1 = (index1?.toString() ?: "").padStart(indexLabelLength1)
            val indexLabel2 = (index2?.toString() ?: "").padStart(indexLabelLength2)
            val line = getLine()
            println(
                when (type) {
                    LineType.COMMON -> "$indexLabel1 $indexLabel2   $line"
                    LineType.DELETED -> RED_BACKGROUND + TEXT_BLACK + "$indexLabel1 $indexLabel2 - $line" + TEXT_RESET
                    LineType.ADDED -> GREEN_BACKGROUND + TEXT_BLACK + "$indexLabel1 $indexLabel2 + $line" + TEXT_RESET
                },
            )
        }
    }

    fun printRangeInfo(startingLine1: Int, lineCount1: Int, startingLine2: Int, lineCount2: Int) {
        val line = "@@ -$startingLine1,$lineCount1 +$startingLine2,$lineCount2 @@"
        println("$BLUE_BACKGROUND$TEXT_BLACK$line$TEXT_RESET")
    }

    // Form a complete list of lines
    val lines: MutableList<Line> = mutableListOf()
    var x = 0
    var y = 0
    for ((i, j) in lcs) {
        while (x < i) {
            lines.add(Line(LineType.DELETED, x + 1, null))
            ++x
        }
        while (y < j) {
            lines.add(Line(LineType.ADDED, null, y + 1))
            ++y
        }
        lines.add(Line(LineType.COMMON, x + 1, y + 1))
        ++x
        ++y
    }

    for (line in lines)
        line.print()
}