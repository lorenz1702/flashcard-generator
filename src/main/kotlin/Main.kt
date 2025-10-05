import com.example.plugin.WebServer


fun main() {
    // Create an instance of your server...
    val server = WebServer(port = 8080)

    // ...and start it.
    server.start()
}