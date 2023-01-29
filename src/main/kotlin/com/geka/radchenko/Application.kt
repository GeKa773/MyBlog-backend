package com.geka.radchenko

import com.geka.radchenko.feature.login.configureLoginRouting
import com.geka.radchenko.feature.register.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.geka.radchenko.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {

    Database.connect(
        url = "jdbc:postgresql://localhost:1122/MyBlog",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "1122"
    )
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()

    configureSerialization()
}
