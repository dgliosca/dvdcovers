import org.apache.http.client.HttpClient
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClients
import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Request
import java.io.InputStream

class DVDCoverCollector {
    val client = ApacheClient(HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setRedirectsEnabled(true)
                    .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                    .build()).build())

    fun coverFor(film: String): InputStream {
        val request = Request(GET, "http://dvdcover.com/search/$film")
        val response = client(request)
        println(response)
        return response.body.stream
    }
}