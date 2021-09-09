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

    // Read
    val fileName1 = args[0]
    val fileName2 = args[1]
    val input1: List<String> = readFile(fileName1)
    val input2: List<String> = readFile(fileName2)

    // Build a trie on all lines from files
    val trie = Trie()
    val lineNodePointer1 = Array<Trie.Node?>(input1.size) { null }
    for (i in input1.indices) {
        lineNodePointer1[i] = trie.insert(input1[i])
    }
    val lineNodePointer2 = Array<Trie.Node?>(input2.size) { null }
    for (i in input2.indices) {
        lineNodePointer2[i] = trie.insert(input2[i])
    }

    val lcs : Array<Pair<Int, Int>> = longestCommonSubsequence(lineNodePointer1, lineNodePointer2)

    printResult(input1, input2, lcs)
}
