import java.io.File

abstract class BaseLevel(private val year:Int,
                         private val number: Int) {

    val lines = getResourceText("$year/level$number.txt")

    private fun getResourceText(path: String): List<String> {
        return File(ClassLoader.getSystemResource(path).file).readLines()
    }

    abstract fun part1()
    abstract fun part2()

    fun solve() {
        part1()
        part2()
    }

}