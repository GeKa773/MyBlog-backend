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
            RegisterController(call).registerNewUser()
        }
    }
}