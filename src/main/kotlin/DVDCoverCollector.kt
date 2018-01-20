import org.apache.http.client.HttpClient
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClients
import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.urlEncoded
import java.io.InputStream

class DVDCoverCollector {
    val client = ApacheClient(HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setRedirectsEnabled(true)
                    .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                    .build()).build())

    fun coverFor(film: String): InputStream {
        val searchRequest = Request(GET, "http://dvdcover.com/search/${film.urlEncoded()}")
        val response = client(searchRequest)
        val pageParser = PageParser()

        val linkAndTitle = pageParser.linkAndTitle(response.body.stream, "UTF8", "http://dvdcover.com/")
        val imageRequest = Request(GET, linkAndTitle.link.toASCIIString())
        val responseImageRequest = client(imageRequest)
        val imageLink = pageParser.linkImage(responseImageRequest.body.stream, "UTF8", "http://dvdcover.com/")
        println("imageLink = ${imageLink}")
        val image = Request(GET, imageLink.toASCIIString())
        val imageResponse = client(image)
        return imageResponse.body.stream
    }
}