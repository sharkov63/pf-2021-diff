import kotlin.test.*
import trie.*

internal class TrieTests {

    @Test
    fun trieTwoEqualOutOfFour() {
        val t = Trie()
        val node1 = t.insert("abacaba")
        val node2 = t.insert("abacabaxx")
        val node3 = t.insert("abb")
        val node4 = t.insert("abacaba")
        assert(node1 !== node2)
        assert(node1 !== node3)
        assert(node1 === node4)
        assert(node2 !== node3)
        assert(node2 !== node4)
        assert(node3 !== node4)
    }

    @Test
    fun trieCyrillicWithLatin() {
        val t = Trie()
        val node1 = t.insert("абб")
        val node2 = t.insert("aббЪ")
        val node3 = t.insert("aбб") // first 'a' is latin, not cyrillic
        val node4 = t.insert("абб")
        assert(node1 !== node2)
        assert(node1 !== node3)
        assert(node1 === node4)
        assert(node2 !== node3)
        assert(node2 !== node4)
        assert(node3 !== node4)
    }

    @Test
    fun trieWeirdSymbols() {
        val t = Trie()
        val node1 = t.insert("\uFDD0\uFDD1\uFDD2\uFDD3\uFDD4\uFDD5\uFDD6\uFDD7\uFDD8\uFDD9\uFDDA\uFDDB\uFDDC\uFDDD\uFDDE\uFDDF\uFDE0\uFDE1\uFDE2\uFDE3\uFDE4\uFDE5\uFDE6\uFDE7\uFDE8\uFDE9\uFDEA\uFDEB\uFDEC\uFDED\uFDEE\uFDEF")
        val node2 = t.insert("������")
        val node3 = t.insert("����������������")
        val node4 = t.insert("\uFDD0\uFDD1\uFDD2\uFDD3\uFDD4\uFDD5\uFDD6\uFDD7\uFDD8\uFDD9\uFDDA\uFDDB\uFDDC\uFDDD\uFDDE\uFDDF\uFDE0\uFDE1\uFDE2\uFDE3\uFDE4\uFDE5\uFDE6\uFDE7\uFDE8\uFDE9\uFDEA\uFDEB\uFDEC\uFDED\uFDEE\uFDEF")
        assert(node1 !== node2)
        assert(node1 !== node3)
        assert(node1 === node4)
        assert(node2 !== node3)
        assert(node2 !== node4)
        assert(node3 !== node4)
    }

    @Test
    fun trieReferenceEquality1() {
        val t = Trie()
        t.insert("ab")
        t.insert("aaa")
        t.insert("aab")
        val node1 = t.insert("a")
        val node2 = t.insert("aa")
        assert(node1 !== node2)
    }

    @Test
    fun trieReferenceEquality2() {
        val t = Trie()
        val node1 = t.insert("a")
        val node2 = t.insert("b")
        assert(node1 !== node2)
        assert(node1 != node2)
        assert(node1.children == node2.children)
    }
}
