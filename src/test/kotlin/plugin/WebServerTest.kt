package plugin

import com.example.plugin.WebServer
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WebServerTest {

    @Test
    fun `server should start and respond to status request`() = runBlocking {
        // ARRANGE
        val port = 8081 // Use a non-default port for tests
        val server = WebServer(port)

        val serverJob = launch {
            server.start()
        }
        Thread.sleep(500)
        val client = HttpClient(CIO)

        try {
            // ACT
            val response = client.get("http://localhost:$port/status")

            // ASSERT
            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals("Server is running...", response.bodyAsText())

        } finally {
            // CLEANUP
            client.close()

            // *** HIER IST DIE KORREKTUR ***
            // Rufe die neue stop() Methode auf, um den Server herunterzufahren
            server.stop()

            // Optional: Warte kurz, bis der Job sich beendet hat, oder cancel ihn jetzt
            serverJob.cancel()
        }
    }
}