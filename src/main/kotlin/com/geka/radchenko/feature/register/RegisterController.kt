package com.geka.radchenko.feature.register

import com.geka.radchenko.database.tokens.TokenDTO
import com.geka.radchenko.database.tokens.Tokens
import com.geka.radchenko.database.users.UserDTO
import com.geka.radchenko.database.users.Users
import com.geka.radchenko.utils.isValidEmail
import data.register.RegisterRequestRemote
import data.register.RegisterResponseRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {

        val registerRequestRemote = call.receive<RegisterRequestRemote>()

        if (!registerRequestRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }

        val userDTO = Users.fetchUser(registerRequestRemote.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {

            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                    UserDTO(
                        login = registerRequestRemote.login,
                        password = registerRequestRemote.password,
                        username = registerRequestRemote.username,
                        email = registerRequestRemote.email,
                        icon = null,
                    )
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }

            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(),
                    login = registerRequestRemote.login,
                    token = token
                )
            )

            call.respond(
                RegisterResponseRemote(
                    login = registerRequestRemote.login,
                    username = registerRequestRemote.username,
                    email = registerRequestRemote.email,
                    token = token
                )
            )
        }
    }
}