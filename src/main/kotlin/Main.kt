import trie.*
import lcs.*
import output.*
import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw Exception("Error: please specify the first file")
    }
    if (args.size == 1) {
        throw Exception("Error: please specify the second file")
    }

    val file1 = File(args[0])
    val file2 = File(args[1])

    // Build a trie on all lines from two files
    // Save links to corresponding trie nodes in two lists

    val trie = Trie()

    val lineNodeLink1: MutableList<Trie.Node> = mutableListOf()
    file1.forEachLine { line ->
        lineNodeLink1.add(trie.insert(line))
    }
    val lineNodeLink2: MutableList<Trie.Node> = mutableListOf()
    file2.forEachLine { line ->
        lineNodeLink2.add(trie.insert(line))
    }

    val lineCount1 = lineNodeLink1.size
    val lineCount2 = lineNodeLink2.size

    val lcs = longestCommonSubsequence(lineNodeLink1.toList(), lineNodeLink2.toList()) { x, y -> x === y }

    printResult(file1, file2, lineCount1, lineCount2, lcs)
}
