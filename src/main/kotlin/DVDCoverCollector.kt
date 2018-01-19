import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Request
import java.io.InputStream

class DVDCoverCollector {
    val client = ApacheClient()

    fun coverFor(film: String): InputStream {
        val request = Request(Method.GET, "http://dvdcover.com/search/$film")
        val response = client(request)
        return response.body.stream
    }
}