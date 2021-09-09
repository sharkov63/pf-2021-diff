import kotlin.test.*
import trie.*

internal class trieTests {

    @Test
    fun trieTest1() {
        val t = Trie()
        val node1 = t.insert("abacaba")
        val node2 = t.insert("abacabaxx")
        val node3 = t.insert("abb")
        val node4 = t.insert("abacaba")
        assertNotEquals(node1, node2)
        assertNotEquals(node1, node3)
        assertEquals(node1, node4)
        assertNotEquals(node2, node3)
        assertNotEquals(node2, node4)
        assertNotEquals(node3, node4)
    }

    @Test
    fun trieTest2() {
        val t = Trie()
        val node1 = t.insert("абб")
        val node2 = t.insert("aббЪ")
        val node3 = t.insert("aбб")
        val node4 = t.insert("абб")
        assertNotEquals(node1, node2)
        assertNotEquals(node1, node3)
        assertEquals(node1, node4)
        assertNotEquals(node2, node3)
        assertNotEquals(node2, node4)
        assertNotEquals(node3, node4)
    }

    @Test
    fun trieTest3() {
        val t = Trie()
        val node1 = t.insert("\uFDD0\uFDD1\uFDD2\uFDD3\uFDD4\uFDD5\uFDD6\uFDD7\uFDD8\uFDD9\uFDDA\uFDDB\uFDDC\uFDDD\uFDDE\uFDDF\uFDE0\uFDE1\uFDE2\uFDE3\uFDE4\uFDE5\uFDE6\uFDE7\uFDE8\uFDE9\uFDEA\uFDEB\uFDEC\uFDED\uFDEE\uFDEF")
        val node2 = t.insert("������")
        val node3 = t.insert("����������������")
        val node4 = t.insert("\uFDD0\uFDD1\uFDD2\uFDD3\uFDD4\uFDD5\uFDD6\uFDD7\uFDD8\uFDD9\uFDDA\uFDDB\uFDDC\uFDDD\uFDDE\uFDDF\uFDE0\uFDE1\uFDE2\uFDE3\uFDE4\uFDE5\uFDE6\uFDE7\uFDE8\uFDE9\uFDEA\uFDEB\uFDEC\uFDED\uFDEE\uFDEF")
        assertNotEquals(node1, node2)
        assertNotEquals(node1, node3)
        assertEquals(node1, node4)
        assertNotEquals(node2, node3)
        assertNotEquals(node2, node4)
        assertNotEquals(node3, node4)
    }
}
