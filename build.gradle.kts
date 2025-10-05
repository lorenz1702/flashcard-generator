plugins {
    kotlin("jvm") version "2.2.10"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val ktorVersion = "2.3.11"
dependencies {
    // --- Ktor ---
    // Ktor Server Dependencies
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // Ktor Test Dependencies
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-client-cio-jvm:$ktorVersion")

    // JUnit 5 Dependency
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    // Ktor needs a logging framework
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // --- Logging ---
    implementation("ch.qos.logback:logback-classic:1.4.14")

    testImplementation("io.ktor:ktor-server-test-host-jvm:2.3.11")

    // --- Testing ---
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.3.9")

    // Google Cloud Translation
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")

    // Google Cloud Text-to-Speech
    implementation("org.json:json:20231013")

    // build.gradle.kts

}

tasks.test {
    useJUnitPlatform()
}