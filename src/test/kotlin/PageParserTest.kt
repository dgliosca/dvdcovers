import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.io.File
import java.net.URI


class PageParserTest {
    
    @Test fun `can get link for the page that contains cover`() {
        val toParse = File(javaClass.classLoader.getResource("search.html").file)
        val pageParser = PageParser()
        val linkAndTitle = pageParser.linkAndTitle(toParse)

        assertThat(linkAndTitle, equalTo(LinkAndTitle(URI("https://dvdcover.com/avatar-2009-r1-dvd-cover/"), "Avatar (2009) R1 DVD Cover")))
    }

    @Test fun `can get image link`() {
        val toParse = File(javaClass.classLoader.getResource("pageWithImageLink.html").file)
        val pageParser = PageParser()
        val imageLink = pageParser.linkImage(toParse)

        assertThat(imageLink, equalTo(URI("https://dvdcover.com/wp-content/uploads/2017/12/2017-12-19_5a396305d47c4_DVD-Avatar-720x483.jpg")))
    }


}