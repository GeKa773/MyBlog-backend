package com.geka.radchenko.feature.blog

import com.geka.radchenko.feature.blog.add.BlogAddController
import com.geka.radchenko.feature.blog.get.BlogGetController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureBlogsRouting() {
    routing {

        post("/add_blog") {
            BlogAddController(call).addNewBlog()
        }
        get("/my_blogs") {
            BlogGetController(call).getMyBlogs()
        }
        get ("/all_blogs"){
            BlogGetController(call).getAllBlogs()
        }
    }
}

