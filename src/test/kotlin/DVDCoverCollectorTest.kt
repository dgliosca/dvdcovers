import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import junit.framework.Assert.fail
import org.junit.Test
import java.io.InputStream

class DVDCoverCollectorTest {

    @Test fun `can get a cover`() {
        val movie = "avatar"
        val dvdCoverCollector = DVDCoverCollector()
        val actualImage = dvdCoverCollector.coverFor(movie)
        val expectedImage = javaClass.classLoader.getResourceAsStream("avatar.jpg")

        assertBinaryContentIsTheSame(actualImage, expectedImage)
    }

    @Test fun `can read movies from file`() {

        val dvdCoverCollector = DVDCoverCollector()
        val inputFile = javaClass.classLoader.getResourceAsStream("fileMovie.txt")
        val movies: List<String> = dvdCoverCollector.readMoviesFromFile(inputFile)

        assertThat(movies[0], equalTo("Fight Club"))
        assertThat(movies[1], equalTo("Independence Day"))
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