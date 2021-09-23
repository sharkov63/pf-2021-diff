import kotlin.test.*
import lcs.*

internal class LcsTests {

    // Checks that longestCommonSubsequence finds a correct common subsequence of a and b with length lcsLength
    private fun <T> lcsTestTemplate(a: List<T>, b: List<T>, lcsLength: Int) {
        val lcs = longestCommonSubsequence(a, b) { x, y -> x == y }
        assertEquals(lcsLength, lcs.size)
        for (t in lcs.indices) {
            val (i, j) = lcs[t]
            assert(a[i] == b[j])
            if (t > 0) {
                assert(lcs[t - 1].first < i)
                assert(lcs[t - 1].second < j)
            }
        }
    }

    @Test
    fun lcsEmptyFirst() {
        val a: List<Int> = listOf()
        val b = listOf(1, 2, 3)
        val lcsLength = 0
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsEmptySecond() {
        val a = listOf(-10, 2)
        val b: List<Int> = listOf()
        val lcsLength = 0
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsEmptyBoth() {
        val a: List<Int> = listOf()
        val b: List<Int> = listOf()
        val lcsLength = 0
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsRandomInt() {
        val a = listOf(1, 2, 3, 1, 2, 2, 3, 1, 4)
        val b = listOf(3, 1, 2, 4, 2, 1)
        val lcsLength = 5
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsEqualArrays() {
        val a = listOf(1, 8, -10, 3, 4, 29, 40, 0, 0, 2)
        val b = listOf(1, 8, -10, 3, 4, 29, 40, 0, 0, 2)
        val lcsLength = 10
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsGreedyBad() {
        val a = listOf(1, 2, 3, 5, 4)
        val b = listOf(2, 3, 4, 1, 5)
        val lcsLength = 3
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsNoCommonElements() {
        val a = listOf(1, 2, 3, 4, 5)
        val b = listOf(6, 7, 8, 9, 10)
        val lcsLength = 0
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsChar() {
        val a = listOf('A', 'A', 'B', 'A')
        val b = listOf('A', 'A', 'A')
        val lcsLength = 3
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsString() {
        val a = listOf("aб", "аб", "а", "й", "я", "й", "й", "а", "я")
        val b = listOf("фй", "-+", "12413213123", "й")
        val lcsLength = 1
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsPairIntInt() {
        val a = List(0) { Pair(0, 0) }
        val b = listOf(Pair(19, 23), Pair(-80, 1337), Pair(11111111, 0))
        val lcsLength = 0
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsTripleIntInt() {
        val a = listOf(Triple(1, 2, 3), Triple(10, 20, 30), Triple(1, 2, 3), Triple(100, 200, 300))
        val b = listOf(Triple(10, 20, 30), Triple(1, 1,  1), Triple(1, 2, 3), Triple(-1, -1, -10))
        val lcsLength = 2
        lcsTestTemplate(a, b, lcsLength)
    }
}
