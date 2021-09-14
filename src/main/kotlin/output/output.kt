package output

import java.lang.Integer.max

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

const val COMMON_LINES_TO_SEPARATE_HUNK = 7
const val MAX_COMMON_LINES_ON_BORDER = 3

enum class LineType {
    COMMON,
    DELETED,
    ADDED,
}

fun printResult(input1: List<String>, input2: List<String>, lcs: Array<Pair<Int, Int>>) {
    val indexLabelLength1 = input1.size.toString().length
    val indexLabelLength2 = input2.size.toString().length

    /*
     * Line data class contains a line that can potentially be printed.
     * A line can be one of three types (common, deleted, added), specified by LineType enum.
     * A line also contains indexes of the corresponding string in input.
     * The string itself is not stored here.
     */
    data class Line(val type: LineType, val index1: Int? = null, val index2: Int? = null) {
        fun getLine(): String {
            return if (index1 != null)
                input1[index1 - 1]
            else if (index2 != null)
                input2[index2 - 1]
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
                    LineType.DELETED -> "$TEXT_RED$indexLabel1 $indexLabel2 - $TEXT_BLACK$RED_BACKGROUND$line$TEXT_RESET"
                    LineType.ADDED -> "$TEXT_GREEN$indexLabel1 $indexLabel2 + $TEXT_BLACK$GREEN_BACKGROUND$line$TEXT_RESET"
                },
            )
        }
    }

    fun printRangeInfo(startingLine1: Int, changedLines1: Int, startingLine2: Int, changedLines2: Int) {
        val line = " ".repeat(indexLabelLength1 + 1 + indexLabelLength2 + 3) + "@@ -$startingLine1,$changedLines1 +$startingLine2,$changedLines2 @@"
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

    // A hunk is a block of several consecutive lines.
    // Two hunks are separated by a consecutive block of $COMMON_LINES_TO_SEPARATE_HUNK or more common lines.
    // Each hunk can contain no more than $MAX_COMMON_LINES_ON_BORDER common lines at its' beginning and its' end.

    if (lines.find { line -> line.type != LineType.COMMON } == null) {
        println(CYAN_BACKGROUND + TEXT_BLACK + "Two files are identical!" + TEXT_RESET)
        return
    }

    var hunkBegin = 0
    while (hunkBegin < lines.size) {
        // Find where the hunk begins:
        // search for next non-common line
        val borderHunkBegin = hunkBegin
        while (hunkBegin < lines.size && lines[hunkBegin].type == LineType.COMMON)
            ++hunkBegin
        if (hunkBegin == lines.size) {
            // No changed lines => no more hunks
            break
        }
        // Add first common lines
        hunkBegin = max(borderHunkBegin, hunkBegin - MAX_COMMON_LINES_ON_BORDER)

        // Find where the hunk ends:
        // search for next large block of common lines
        var hunkEnd = hunkBegin
        var consectutiveCommonLines = 0
        while (hunkEnd < lines.size) {
            if (lines[hunkEnd].type == LineType.COMMON) {
                ++consectutiveCommonLines
                if (consectutiveCommonLines >= COMMON_LINES_TO_SEPARATE_HUNK) {
                    // Found a large enough block of common lines
                    // Hunk ends here somewhere
                    hunkEnd = hunkEnd - COMMON_LINES_TO_SEPARATE_HUNK + MAX_COMMON_LINES_ON_BORDER + 1
                    break
                }
            } else {
                consectutiveCommonLines = 0
            }
            ++hunkEnd
        }

        // Now hunk is ready to be printed

        // Print range info
        var startingLine1 = 0
        var changedLines1 = 0
        var startingLine2 = 0
        var changedLines2 = 0
        for (i in hunkBegin until hunkEnd) {
            val line = lines[i]
            if (line.index1 != null) {
                if (startingLine1 == 0)
                    startingLine1 = line.index1
                ++changedLines1
            }
            if (line.index2 != null) {
                if (startingLine2 == 0)
                    startingLine2 = line.index2
                ++changedLines2
            }
        }
        printRangeInfo(startingLine1, changedLines1, startingLine2, changedLines2)

        // Now print lines in the hunk
        for (i in hunkBegin until hunkEnd)
            lines[i].print()

        // Move on to the next hunk
        hunkBegin = hunkEnd
    }
}