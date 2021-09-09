import input.*
import trie.*
import lcs.*
import output.*

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw Exception("Error: please specify the first file")
    }
    if (args.size == 1) {
        throw Exception("Error: please specify the second file")
    }

    val fileName1 = args[0]
    val fileName2 = args[1]
    val input1: List<String> = readFile(fileName1)
    val input2: List<String> = readFile(fileName2)

    //var trie = Trie()
    var lineClassNumber1 = Array<Int>(input1.size, {0})
    TODO("Add input1 to trie")
    TODO("Calculate lineClassNumber1")
    var lineClassNumber2 = Array<Int>(input2.size, {0})
    TODO("Add input2 to trie")
    TODO("Calculate lineClassNumber2")

    val lcs : Array<Pair<Int, Int>> = longestCommonSubsequence(lineClassNumber1, lineClassNumber2)

    printResult(input1, input2, lcs)
}
