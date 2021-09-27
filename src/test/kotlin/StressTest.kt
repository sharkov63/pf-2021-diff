import kotlin.test.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.system.measureTimeMillis


internal class StressTest {
    private val stdin = System.`in`
    private val stdout = System.out

    private val pathToTests = "testData/stressTests/"
    private val testList = listOf(
        "long750",
        "long1000",
        "long1500",
        "long2000",
        "long2000",
        "long3000",
        "long4000",
        "long5000",
        "long7500",
        "long10000",
        "long15000",
        "long20000",
        "long30000",
        "long50000",
        "wide100",
        "wide125",
        "wide150",
        "wide175",
        "wide200",
        "wide250",
        "wide300",
        "wide350",
        "wide400",
        "wide500",
    )

    @Test
    fun run() {
        val tempStream = ByteArrayOutputStream()
        for (testName in testList) {
            val filename1 = "$pathToTests$testName-1"
            val filename2 = "$pathToTests$testName-2"
            System.setOut(PrintStream(tempStream))
            val time = measureTimeMillis { main(arrayOf(filename1, filename2)) }
            System.setOut(stdout)
            println("$testName: $time ms")
        }
    }
}