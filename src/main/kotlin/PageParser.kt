import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.InputStream
import java.net.URI

class PageParser {

    fun linkAndTitle(input: File): LinkAndTitle {
        val doc = Jsoup.parse(input, "UTF8")
        return linkeAntTitle(doc)
    }

    fun linkAndTitle(input: InputStream, charsetName: String = "UTF8", baseUri: String): LinkAndTitle {
        val doc = Jsoup.parse(input, charsetName, baseUri)
        return linkeAntTitle(doc)
    }

    private fun linkeAntTitle(doc: Document): LinkAndTitle {
        val elementsByTag = doc.getElementsByTag("article")
        val elementsByClass = elementsByTag.first().getElementsByClass("preview")
        val link = elementsByClass.first().getElementsByTag("a").first().attr("href")
        val title = elementsByClass.first().getElementsByTag("a").first().attr("title")
        return LinkAndTitle(URI(link), title)
    }

    fun linkImage(input: File) : URI {
        val doc = Jsoup.parse(input, "UTF8")
        return imageLink(doc)
    }

    fun linkImage(input: InputStream, charsetName: String = "UTF8", baseUri: String): URI {
        val doc = Jsoup.parse(input, charsetName, baseUri)
        return imageLink(doc)
    }

    private fun imageLink(doc: Document): URI {
        val elementsByClass = doc.getElementsByClass("featured-image").first()
        return URI(elementsByClass.getElementsByTag("img").first().attr("src"))
    }


}