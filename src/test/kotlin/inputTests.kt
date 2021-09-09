import kotlin.test.*
import input.*

internal class inputTests {

    @Test
    fun inputTest() {
        val answer1 = listOf("Hello Kotlin!")
        assertEquals(readFile("src/test/kotlin/inputTest1.txt"), answer1)
        val answer2 = listOf("Hello", "*7^&-+=1", ".", "/", "\\\\", "\\n", "~", "     ", " ", ")", "")
        assertEquals(readFile("src/test/kotlin/inputTest2.txt"), answer2)
    }
}
