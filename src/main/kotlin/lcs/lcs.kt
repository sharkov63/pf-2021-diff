package lcs

enum class LcsRestoreInfo {
    TAKE_BOTH,
    SKIP_A,
    SKIP_B,
    UNKNOWN,
}

/*
 * Finds any longest common subsequence (LCS) of two sequences a and b
 * using quadratic dynamic programming approach.
 *
 * Uses "equals" predicate to decide of two elements of type T are equal.
 *
 * Returns an array of pairs (i, j),
 * where first indices form the found subsequence in a,
 * and second indices form the found subsequence in b.
 */
fun <T> longestCommonSubsequence(a: Array<T>, b: Array<T>, equals: (T, T) -> Boolean): Array<Pair<Int, Int>> {
    // lcsLen[i][j] is the length of the longest common subsequence of prefixes a[0..i-1] and b[0..j-1]
    val lcsLen = Array(a.size + 1) { Array(b.size + 1) { 0 } }



    // lcsRestoreInfo[i][j] instructs what to do with last elements
    // of prefixes a[0..i-1] and b[0..j-1] to get the LCS:
    //   TAKE_BOTH   if both elements a[i - 1] and b[j - 1] are taken in the subsequence
    //   SKIP_A      if a[i - 1] is not taken in the subsequence
    //   SKIP_B      if b[j - 1] is not taken in the subsequence
    val lcsRestoreInfo = Array(a.size + 1) { Array(b.size + 1) { LcsRestoreInfo.UNKNOWN } }

    fun recalc(i: Int, j: Int) {
        if (lcsLen[i - 1][j] >= lcsLen[i][j - 1]) {
            // throw away a[i - 1]
            lcsLen[i][j] = lcsLen[i - 1][j]
            lcsRestoreInfo[i][j] = LcsRestoreInfo.SKIP_A
        } else {
            // throw away b[j - 1]
            lcsLen[i][j] = lcsLen[i][j - 1]
            lcsRestoreInfo[i][j] = LcsRestoreInfo.SKIP_B
        }
        if (equals(a[i - 1], b[j - 1]) && lcsLen[i - 1][j - 1] + 1 > lcsLen[i][j]) {
            // take both a[i - 1] and b[j - 1]
            lcsLen[i][j] = lcsLen[i - 1][j - 1] + 1
            lcsRestoreInfo[i][j] = LcsRestoreInfo.TAKE_BOTH
        }
    }

    // Calculating DP
    for (i in 1..a.size) {
        for (j in 1..b.size) {
            recalc(i, j)
        }
    }

    var i = a.size
    var j = b.size
    var len = lcsLen[i][j]
    val answer = Array(len) { Pair(-1, -1) }
    // Restore the LCS backwards using lcsPrv
    while (len > 0) {
        when (lcsRestoreInfo[i][j]) {
            LcsRestoreInfo.TAKE_BOTH -> {
                --i
                --j
                answer[--len] = Pair(i, j)
            }
            LcsRestoreInfo.SKIP_A -> i--
            LcsRestoreInfo.SKIP_B -> j--
            else -> break
        }
    }
    return answer
}