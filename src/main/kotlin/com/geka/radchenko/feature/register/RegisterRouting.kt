package com.geka.radchenko.feature.register

import com.geka.radchenko.cache.InMemoryCache
import com.geka.radchenko.cache.TokenCache
import com.geka.radchenko.utils.isValidEmail
import data.register.RegisterRequestRemote
import data.register.RegisterResponseRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID


fun Application.configureRegisterRouting() {
    routing() {
        post("/register") {
            val receive = call.receive<RegisterRequestRemote>()
            if (!receive.email.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            }

            if (InMemoryCache.userList.map { it.login }.contains(receive.login)) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }

            val token = UUID.randomUUID().toString()
            InMemoryCache.userList.add(receive)
            InMemoryCache.token.add(TokenCache(login = receive.login, email = receive.email, token = token))

            call.respond(RegisterResponseRemote(login = receive.login, email = receive.email, token = token))
        }
    }
}