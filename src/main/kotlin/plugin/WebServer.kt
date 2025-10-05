package com.example.plugin

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.concurrent.TimeUnit

class WebServer(private val port: Int) {

    // A variable to hold the running server instance
    private var server: ApplicationEngine? = null

    fun start() {
        // Create the server but don't call .start() immediately
        server = embeddedServer(Netty, port = port) {
            routing {
                get("/") {
                    call.respondText("Hello from the WebServer class!")
                }
                get("/status") {
                    call.respondText("Server is running...")
                }
            }
        }
        // Start the server
        server?.start(wait = true)
    }

    // A new method to gracefully shut down the server
    fun stop() {
        println("Stopping Ktor server...")
        server?.stop(gracePeriodMillis = 100, timeoutMillis = 500)
    }
}