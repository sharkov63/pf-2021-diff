package lcs

enum class LcsRestoreInfo {
    TAKE_BOTH,
    SKIP_A,
    SKIP_B,
    UNKNOWN,
}

/*
 * Finds any longest common subsequence (LCS) of two lists a and b.
 *
 * Uses "equals" predicate to decide of two elements of type T are equal.
 *
 * Returns a list of pairs (i, j),
 * where first indices form the found subsequence in a,
 * and second indices form the found subsequence in b.
 *
 * The algorithm is "Hirschberg algorithm" derived from usual quadratic DP approach,
 * which runs in O(N*M) time and O(M + log(n)) space.
 */
fun <T> longestCommonSubsequence(a: List<T>, b: List<T>, equals: (T, T) -> Boolean): List<Pair<Int, Int>> {
    if (a.isEmpty() || b.isEmpty()) return listOf()

    data class DpState(val len: Int = 0, val link: Int = -1)

    val dpLayer = Array(2) { Array(b.size + 1) { DpState() } }
    val answer: MutableList<Pair<Int, Int>> = mutableListOf()

    /*
     * solve(start1, end1, start2, end2) finds a LCS of sequences a[start1, end1) and b[start2, end2).
     * The corresponding pairs (i, j) of the LCS are pushed back into answer.
     * This function uses two global layers (dpLayer[0/1]) to calculate DP for the sake of optimisation.
     */
    fun solve(start1: Int, end1: Int, start2: Int, end2: Int) {
        if (end1 <= start1) return
        if (end1 - start1 == 1) {
            // There is only one element in the first sequence => just do naive check
            for (j in start2 until end2) {
                if (a[start1] == b[j]) {
                    answer.add(Pair(start1, j))
                    break
                }
            }
            return
        }

        // Split the rows by half
        val mid1: Int = (start1 + end1) / 2

        // We calculate normal quadratic DP,
        // but instead of storing usual restore information,
        // we store an index j, which means
        // the optimal LCS path visits state (mid1, j).

        // Prepare two global DP layers.
        // Note that the first index i is always taken modulo 2.
        dpLayer[0].fill(DpState(), start2, end2 + 1)
        dpLayer[1].fill(DpState(), start2, end2 + 1)
        for (i in start1 + 1..end1) {
            // Normal DP relaxation
            dpLayer[i % 2][start2] = dpLayer[(i % 2) xor 1][start2]
            for (j in start2 + 1..end2) {
                if (dpLayer[(i % 2) xor 1][j].len >= dpLayer[i % 2][j - 1].len) {
                    dpLayer[i % 2][j] = dpLayer[(i % 2) xor 1][j]
                } else {
                    dpLayer[i % 2][j] = dpLayer[i % 2][j - 1]
                }
                if (equals(a[i - 1], b[j - 1]) && dpLayer[(i % 2) xor 1][j - 1].len + 1 > dpLayer[i % 2][j].len) {
                    dpLayer[i % 2][j] = DpState(dpLayer[(i % 2) xor 1][j - 1].len + 1, dpLayer[(i % 2) xor 1][j - 1].link)
                }
            }
            // We reached the row mid1, now we should always remember at which column we visited it
            if (i == mid1)
                for (j in start2..end2)
                    dpLayer[i % 2][j] = DpState(dpLayer[i % 2][j].len, j)
        }

        val mid2 = dpLayer[end1 % 2][end2].link

        // Now we know the optimal LCS path visits (mid1, mid2)
        // So we can split our task into two subtasks.
        solve(start1, mid1, start2, mid2)
        solve(mid1, end1, mid2, end2)
    }

    solve(0, a.size, 0, b.size)

    return answer.toList()
}