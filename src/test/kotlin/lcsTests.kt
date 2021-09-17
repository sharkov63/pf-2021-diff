import kotlin.test.*
import lcs.*

internal class lcsTests {

    private fun <T> lcsTestTemplate(a: List<T>, b: List<T>, lcsLength: Int) {
        val lcs = longestCommonSubsequence(a, b) { x, y -> x == y }
        assertEquals(lcs.size, lcsLength)
        for (t in lcs.indices) {
            val (i, j) = lcs[t]
            assertEquals(a[i], b[j])
            if (t > 0) {
                assert(lcs[t - 1].first < i)
                assert(lcs[t - 1].second < j)
            }
        }
    }

    @Test
    fun lcsTest1() {
        val a = listOf(1, 2, 3, 1, 2, 2, 3, 1, 4)
        val b = listOf(3, 1, 2, 4, 2, 1)
        val lcsLength = 5
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsTest2() {
        val a = listOf('A', 'A', 'B', 'A')
        val b = listOf('A', 'A', 'A')
        val lcsLength = 3
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsTest3() {
        val a = listOf("aб", "аб", "а", "й", "я", "й", "й", "а", "я")
        val b = listOf("фй", "-+", "12413213123", "й")
        val lcsLength = 1
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsTest4() {
        val a = List(0) { Pair(0, 0) }
        val b = listOf(Pair(19, 23), Pair(-80, 1337), Pair(11111111, 0))
        val lcsLength = 0
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsTest5() {
        val a = listOf(Triple(1, 2, 3), Triple(10, 20, 30), Triple(1, 2, 3), Triple(100, 200, 300))
        val b = listOf(Triple(10, 20, 30), Triple(1, 1,  1), Triple(1, 2, 3), Triple(-1, -1, -10))
        val lcsLength = 2
        lcsTestTemplate(a, b, lcsLength)
    }
}
