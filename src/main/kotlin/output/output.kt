package output

import java.io.File

// Text
const val TEXT_RESET = "\u001B[0m"
const val TEXT_BLACK = "\u001B[30m"
const val TEXT_RED = "\u001B[31m"
const val TEXT_GREEN = "\u001B[32m"

// Background
const val RED_BACKGROUND = "\u001b[41m"
const val GREEN_BACKGROUND = "\u001b[42m"
const val BLUE_BACKGROUND = "\u001b[44m"
const val CYAN_BACKGROUND = "\u001b[46m"

const val COMMON_LINES_TO_SEPARATE_HUNK = 7
const val MAX_COMMON_LINES_ON_BORDER = 3

enum class LineType {
    COMMON,
    DELETED,
    ADDED,
}

/**
 * Prints the diff, given [file1] and [file2],
 * their corresponding [lineCount1] and [lineCount2]
 * and the found longest common subsequence [lcs].
 */
fun printResult(file1: File, file2: File, lineCount1: Int, lineCount2: Int, lcs: List<Pair<Int, Int>>) {
    val indexLabelLength1 = lineCount1.toString().length
    val indexLabelLength2 = lineCount2.toString().length

    /**
     * LineReader successively reads lines from given [file], as they are requiered.
     */
    class LineReader(file: File) {
        val reader = file.bufferedReader()
        var nextLineToRead = 0
        var currentLine = ""

        fun getLineByIndex(index: Int): String {
            while (nextLineToRead <= index) {
                currentLine = reader.readLine()
                nextLineToRead++
            }
            return currentLine
        }
    }

    val lreader1 = LineReader(file1)
    val lreader2 = LineReader(file2)


    /**
     * [Line] data class contains a line that can potentially be printed.
     * A line can be one of three types (common, deleted, added), specified by [LineType] enum.
     * A line also contains indexes of the corresponding string in input.
     * The string itself is not stored here.
     */
    data class Line(val type: LineType, val index1: Int? = null, val index2: Int? = null) {
        fun getLine() = when {
            index1 != null -> lreader1.getLineByIndex(index1 - 1)
            index2 != null -> lreader2.getLineByIndex(index2 - 1)
            else -> ""
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
        val tabs = " ".repeat(indexLabelLength1 + 1 + indexLabelLength2 + 3)
        val rangeInfo = "@@ -$startingLine1,$changedLines1 +$startingLine2,$changedLines2 @@"
        println("$BLUE_BACKGROUND$TEXT_BLACK$tabs$rangeInfo$TEXT_RESET")
    }

    // Form a complete list of lines
    var lines: MutableList<Line> = mutableListOf()
    var nextLineIndex1 = 0
    var nextLineIndex2 = 0
    for ((lineIndex1, lineIndex2) in lcs) {
        while (nextLineIndex1 < lineIndex1) {
            lines.add(Line(LineType.DELETED, nextLineIndex1 + 1, null))
            ++nextLineIndex1
        }
        while (nextLineIndex2 < lineIndex2) {
            lines.add(Line(LineType.ADDED, null, nextLineIndex2 + 1))
            ++nextLineIndex2
        }
        lines.add(Line(LineType.COMMON, lineIndex1 + 1, lineIndex2 + 1))
        ++nextLineIndex1
        ++nextLineIndex2
    }

    if (lines.find { it.type != LineType.COMMON } == null) {
        println(CYAN_BACKGROUND + TEXT_BLACK + "Two files are identical!" + TEXT_RESET)
        return
    }

    // A hunk is a block of several consecutive lines.
    // Two hunks are separated by a consecutive block of $COMMON_LINES_TO_SEPARATE_HUNK or more common lines.
    // Each hunk can contain no more than $MAX_COMMON_LINES_ON_BORDER common lines at its' beginning and its' end.

    while (lines.find { it.type != LineType.COMMON } != null) {
        // A non-common line upcoming => a new hunk begins

        // Drop explicit common lines before the hunk
        val hunkBegin = lines.indexOfFirst { it.type != LineType.COMMON }
        if (hunkBegin > MAX_COMMON_LINES_ON_BORDER) {
            val dropN = hunkBegin - MAX_COMMON_LINES_ON_BORDER
            lines = lines.drop(dropN).toMutableList()
        }

        // Find where the hunk ends

        // Count the number of consecutive common lines up until every point
        val commonLinesBlockLength = lines.scan(0) { commonLines, line ->
            if (line.type == LineType.COMMON)
                commonLines + 1
            else
                0
        }
        // The hunk ends when the block of consecutive common lines is too big
        val commonBlockEnd = commonLinesBlockLength.indexOfFirst { it >= COMMON_LINES_TO_SEPARATE_HUNK }
        val hunkEnd = if (commonBlockEnd == -1)
            lines.size
        else
            commonBlockEnd - COMMON_LINES_TO_SEPARATE_HUNK + MAX_COMMON_LINES_ON_BORDER

        // Now slice the hunk out
        val hunk = lines.slice(0 until hunkEnd)

        // Print range info
        val firstLine1 = hunk.firstOrNull { it.type != LineType.ADDED }
        val firstLine2 = hunk.firstOrNull { it.type != LineType.DELETED }
        val startingLine1 = firstLine1?.index1 ?: 0
        val changedLines1 = hunk.count { it.type != LineType.ADDED }
        val startingLine2 = firstLine2?.index2 ?: 0
        val changedLines2 = hunk.count { it.type != LineType.DELETED }
        printRangeInfo(startingLine1, changedLines1, startingLine2, changedLines2)

        // Print the hunk
        hunk.forEach { it.print() }

        // Drop the hunk from the list
        lines = lines.drop(hunk.size).toMutableList()
    }
}