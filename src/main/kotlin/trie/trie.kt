package trie

/*
 * A pointer implementation of "trie" data structure.
 * See https://en.wikipedia.org/wiki/Trie
 */
class Trie {
    /*
     * A node in trie.
     * Links to children nodes are contained in a MutableMap.
     */
    class Node {
        var children : MutableMap<Char, Node?> = mutableMapOf()
    }

    private val root = Node()

    /*
     * Insert a string into trie.
     * Returns a pointer to the corresponding node in the trie.
     */
    fun insert(str: String): Node? {
        // We maintain a pointer to current node, and move down
        var curNode: Node? = root
        for (ch in str) {
            if (!curNode!!.children.containsKey(ch)) {
                // There is no downward edge with symbol "ch", so we create one
                curNode.children[ch] = Node()
            }
            curNode = curNode.children[ch]
        }
        return curNode
    }
}