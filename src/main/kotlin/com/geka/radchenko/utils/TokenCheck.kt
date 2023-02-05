package com.geka.radchenko.utils

import com.geka.radchenko.database.tokens.TokenDTO
import com.geka.radchenko.database.tokens.Tokens
import com.geka.radchenko.database.users.UserDTO
import com.geka.radchenko.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

object TokenCheck {
    fun isTokenValid(token: String): TokenDTO? = Tokens.fetchToken(token)

    suspend inline fun getUserFromToken(call: ApplicationCall, ifAuthorization: (userDTO: UserDTO) -> Unit) {

        val request = call.request.headers["Bearer-Authorization"]

        TokenCheck.isTokenValid(request.orEmpty()).let { tokenDTO ->
            if (tokenDTO == null) {
                call.respond(HttpStatusCode.Unauthorized, "Token expired")
            } else {
                val user = Users.fetchUser(tokenDTO.login) ?: kotlin.run {
                    call.respond(HttpStatusCode.InternalServerError, "unable to find user by token")
                    return@let
                }
                ifAuthorization.invoke(user)
            }
        }
    }
}