package com.geka.radchenko.feature.blog

import com.geka.radchenko.feature.blog.add.BlogAddController
import com.geka.radchenko.feature.blog.delete.BlogDeleteController
import com.geka.radchenko.feature.blog.get.BlogGetController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureBlogsRouting() {
    routing {

        post("/blog/add") {
            BlogAddController(call).addNewBlog()
        }
        get("/blog/my") {
            BlogGetController(call).getMyBlogs()
        }
        get("/blog/all") {
            BlogGetController(call).getAllBlogs()
        }
        delete("/blog/delete/{id?}") {
            BlogDeleteController(call).deleteBlog()
        }
    }
}

