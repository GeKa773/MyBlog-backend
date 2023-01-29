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
            LoginController(call).performLogin()
        }
    }

}