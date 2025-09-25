package org.example.application

interface ScriptRunner {
    fun run(scriptPath: String, csvPath: String, outputPath: String)
}