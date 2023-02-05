package com.geka.radchenko.feature.blog.delete

import com.geka.radchenko.database.blogs.Blogs
import com.geka.radchenko.database.tokens.Tokens
import com.geka.radchenko.utils.TokenCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class BlogDeleteController(private val call: ApplicationCall) {

    suspend fun deleteBlog() {
        TokenCheck.getUserFromToken(call) { userDTO ->

            val id = call.parameters["id"]?.toIntOrNull() ?: return@getUserFromToken call.respond(
                HttpStatusCode.BadRequest,
                "did not pass id"
            )

            val blog = Blogs.fetchBlog(id) ?: kotlin.run {
                call.respond(HttpStatusCode.NotFound, "blog not found")
                return@getUserFromToken
            }

            if (blog.user.login != userDTO.login) {
                call.respond(HttpStatusCode.BadRequest, "Can't delete someone else's blog")
            }

            Blogs.deleteBlog(id)
            call.respond(HttpStatusCode.OK, "blog deleted")
        }
    }
}