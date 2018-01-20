import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import junit.framework.Assert.fail
import org.junit.Test
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

class DVDCoverCollectorTest {

    @Test
    fun `can get a cover`() {
        val movie = "avatar"
        val dvdCoverCollector = DVDCoverCollector()
        val actualImage = dvdCoverCollector.coverFor(movie)
        val expectedImage = javaClass.classLoader.getResourceAsStream("avatar.jpg")

        assertBinaryContentIsTheSame(actualImage, expectedImage)
    }

    @Test
    fun `can read movies from file`() {
        val dvdCoverCollector = DVDCoverCollector()
        val inputFile = javaClass.classLoader.getResourceAsStream("fileMovie.txt")
        val movies: List<String> = dvdCoverCollector.readMoviesFromFile(inputFile)

        assertThat(movies[0], equalTo("Fight Club"))
        assertThat(movies[1], equalTo("Independence Day"))
    }

    @Test
    fun `can download cover to a specified folder`() {
        val dvdCoverCollector = DVDCoverCollector()
        val movie = "mona lisa smile"
        dvdCoverCollector.downloadCover(movie, "covers")

        val downloadFolder = Paths.get("covers").toAbsolutePath()
        val listFiles = downloadFolder.toFile().listFiles()
        val file = FileInputStream(listFiles[0])
        val expected = javaClass.classLoader.getResourceAsStream("mona lisa smile.jpg")

        assertBinaryContentIsTheSame(file, expected)
        for (file in listFiles) file.delete()
        Files.deleteIfExists(downloadFolder)
    }

    private fun assertBinaryContentIsTheSame(binaryInputA: InputStream, binaryInputB: InputStream): Boolean {
        val byteReadA = binaryInputA.buffered()
        val byteReadB = binaryInputB.buffered()

        byteReadA.use { readA ->
            byteReadB.use { readB ->
                val byteArrayA = ByteArray(1)
                val byteArrayB = ByteArray(1)
                do {
                    val contentA = readA.read(byteArrayA)
                    val contentB = readB.read(byteArrayB)
                    val match = (contentA == contentB) && byteArrayA.contentEquals(byteArrayB)
                    if (!match)
                        fail("The content is different")
                } while (contentA != -1 && contentB != -1)
            }
        }
        return true
    }
}