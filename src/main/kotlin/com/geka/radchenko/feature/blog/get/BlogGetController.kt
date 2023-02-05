package com.geka.radchenko.feature.blog.get

import com.geka.radchenko.database.blogs.Blogs
import com.geka.radchenko.utils.TokenCheck
import data.blog.BlogResponseRemote
import data.user.UserData
import io.ktor.server.application.*
import io.ktor.server.response.*

class BlogGetController(private val call: ApplicationCall) {
    suspend fun getMyBlogs() {
        TokenCheck.getUserFromToken(call) { userDTO ->

            val user = UserData(
                login = userDTO.login,
                username = userDTO.username,
                email = userDTO.email,
                icon = userDTO.icon
            )

            val list = Blogs.fetchUserBlogList(userDTO.login).map {
                BlogResponseRemote(
                    id = it.id,
                    user = user,
                    title = it.title,
                    body = it.body,
                    image = it.image,
                    likes = it.likes,
                )
            }
            call.respond(list)
        }
    }

    suspend fun getAllBlogs() {
        TokenCheck.getUserFromToken(call) {

            val list = Blogs.fetchBlogsList().map {
                BlogResponseRemote(
                    id = it.id,
                    user = UserData(
                        login = it.user.login,
                        username = it.user.username,
                        email = it.user.email,
                        icon = it.user.icon
                    ),
                    title = it.title,
                    body = it.body,
                    image = it.image,
                    likes = it.likes,
                )
            }

            call.respond(list)
        }
    }
}