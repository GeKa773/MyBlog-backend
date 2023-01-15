package com.geka.radchenko.feature.login

import com.geka.radchenko.cache.InMemoryCache
import com.geka.radchenko.cache.TokenCache
import data.login.LoginRequestRemote
import data.login.LoginResponseRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureLoginRouting() {
    routing() {
        post("/login") {
            val receive = call.receive<LoginRequestRemote>()
            val first = InMemoryCache.userList.firstOrNull{it.login == receive.login}

            if (first == null){
                call.respond(HttpStatusCode.BadRequest, "User not found")
            } else {
                if (first.password == receive.password){
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.token.add(TokenCache(login = receive.login, token = token))
                    call.respond(LoginResponseRemote(token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid password")
                }
            }
        }
    }

}