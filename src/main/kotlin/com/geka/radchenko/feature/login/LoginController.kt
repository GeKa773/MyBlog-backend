package com.geka.radchenko.feature.login

import com.geka.radchenko.database.tokens.TokenDTO
import com.geka.radchenko.database.tokens.Tokens
import com.geka.radchenko.database.users.Users
import data.login.LoginRequestRemote
import data.login.LoginResponseRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginRequestRemote>()
        val userDTO = Users.fetchUser(receive.login)

        println("receive -> $receive  ,  dto -> $userDTO")
        when {
            userDTO == null -> call.respond(HttpStatusCode.BadRequest, "User not found")
            userDTO.password != receive.password -> call.respond(HttpStatusCode.BadRequest, "Invalid password")
            else -> {
                val token = UUID.randomUUID().toString()

                Tokens.insert(
                    TokenDTO(
                        rowId = UUID.randomUUID().toString(),
                        login = receive.login,
                        token = token
                    )
                )
                call.respond(
                    LoginResponseRemote(
                        login = userDTO.login,
                        username = userDTO.username,
                        email = userDTO.email,
                        token
                    )
                )
            }
        }
    }
}