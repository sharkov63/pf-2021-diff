import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.test.*

internal class DiffTests {
    private val stdin = System.`in`
    private val stdout = System.out
    private val testFolderPath = "testData/diffTests/"
    private val stream = ByteArrayOutputStream()

    fun diffTestTemplate(testName: String) {
        val testPath = "$testFolderPath$testName"

        System.setOut(PrintStream(stream))
        main(arrayOf("$testPath-1", "$testPath-2"))
        System.setOut(stdout)

        val programOutput = stream.toString()

        val ofile = File("$testPath-o")
        val expectedOutput = ofile.readText()
        assertEquals(expectedOutput, programOutput)
    }

    @Test
    fun diffSimpleTest() {
        diffTestTemplate("diffSimpleTest")
    }

    @Test
    fun diffEqualFiles() {
        diffTestTemplate("diffEqualFiles")
    }

    @Test
    fun diffCommon5Begin() {
        diffTestTemplate("diffCommon5Begin")
    }

    @Test
    fun diffCommon10Begin() {
        diffTestTemplate("diffCommon10Begin")
    }

    @Test
    fun diffCommon5End() {
        diffTestTemplate("diffCommon5End")
    }

    @Test
    fun diffCommon10End() {
        diffTestTemplate("diffCommon10End")
    }

    @Test
    fun diffCommon5BeginEnd() {
        diffTestTemplate("diffCommon5BeginEnd")
    }

    @Test
    fun diffCommon10BeginEnd() {
        diffTestTemplate("diffCommon10BeginEnd")
    }

    @Test
    fun diffAddedManyLines() {
        diffTestTemplate("diffAddedManyLines")
    }

    @Test
    fun diffAdded6Begin() {
        diffTestTemplate("diffAdded6Begin")
    }

    @Test
    fun diffAdded8End() {
        diffTestTemplate("diffAdded8End")
    }

    @Test
    fun diffDeletedManyLines() {
        diffTestTemplate("diffDeletedManyLines")
    }

    @Test
    fun diffDeleted8Begin() {
        diffTestTemplate("diffDeleted8Begin")
    }

    @Test
    fun diffDeleted6End() {
        diffTestTemplate("diffDeleted6End")
    }

    @Test
    fun diffCommon7Middle() {
        diffTestTemplate("diffCommon7Middle")
    }

    @Test
    fun diffCommon10Middle() {
        diffTestTemplate("diffCommon10Middle")
    }
}