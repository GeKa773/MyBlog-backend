package com.geka.radchenko.feature.blog.add

import com.geka.radchenko.database.blogs.BlogDTO
import com.geka.radchenko.database.blogs.Blogs
import com.geka.radchenko.database.tokens.Tokens
import com.geka.radchenko.utils.TokenCheck
import data.blog.BlogRequestRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class BlogAddController(private val call: ApplicationCall) {
    suspend fun addNewBlog() {
        TokenCheck.getUserFromToken(call) { userDTO ->
            val receive = call.receive<BlogRequestRemote>()

            val blogId = Blogs.getBlogIdForInsert() ?: kotlin.run {
                call.respond(HttpStatusCode.InternalServerError, "failed to give blog id")
                return@getUserFromToken
            }

            Blogs.insert(
                BlogDTO(
                    id = blogId,
                    user = userDTO,
                    title = receive.title,
                    body = receive.body,
                    image = receive.image,
                    likes = 0,
                )
            )
            call.respond(HttpStatusCode.OK, "Blog add")
        }
    }
}