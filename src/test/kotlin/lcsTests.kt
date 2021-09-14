import kotlin.test.*
import lcs.*

internal class lcsTests {

    private fun <T> lcsTestTemplate(a: Array<T>, b: Array<T>, lcsLength: Int) {
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
        val a = arrayOf(1, 2, 3, 1, 2, 2, 3, 1, 4)
        val b = arrayOf(3, 1, 2, 4, 2, 1)
        val lcsLength = 5
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsTest2() {
        val a = arrayOf('A', 'A', 'B', 'A')
        val b = arrayOf('A', 'A', 'A')
        val lcsLength = 3
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsTest3() {
        val a = arrayOf("aб", "аб", "а", "й", "я", "й", "й", "а", "я")
        val b = arrayOf("фй", "-+", "12413213123", "й")
        val lcsLength = 1
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsTest4() {
        val a = Array(0) { Pair(0, 0) }
        val b = arrayOf(Pair(19, 23), Pair(-80, 1337), Pair(11111111, 0))
        val lcsLength = 0
        lcsTestTemplate(a, b, lcsLength)
    }

    @Test
    fun lcsTest5() {
        val a = arrayOf(Triple(1, 2, 3), Triple(10, 20, 30), Triple(1, 2, 3), Triple(100, 200, 300))
        val b = arrayOf(Triple(10, 20, 30), Triple(1, 1,  1), Triple(1, 2, 3), Triple(-1, -1, -10))
        val lcsLength = 2
        lcsTestTemplate(a, b, lcsLength)
    }
}
