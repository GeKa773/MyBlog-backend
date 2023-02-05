package com.geka.radchenko.database.blogs

import com.geka.radchenko.database.users.UserDTO
import com.geka.radchenko.database.users.Users
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Blogs : Table() {

    private val id = Blogs.integer("id")
    private val userId = Blogs.varchar("userId", 100)
    private val title = Blogs.varchar("title", 100)
    private val body = Blogs.varchar("body", 1_000).nullable()
    private val image = Blogs.varchar("image", 50).nullable()
    private val likes = Blogs.integer("likes")

    fun insert(blogDTO: BlogDTO) {
        transaction {
            Blogs.insert {
                it[id] = blogDTO.id
                it[userId] = blogDTO.user.login
                it[title] = blogDTO.title
                it[body] = blogDTO.body
                it[image] = blogDTO.image
                it[likes] = blogDTO.likes
            }
        }
    }

    fun fetchBlogsList(): List<BlogDTO> {
        return try {
            transaction {
                Blogs.selectAll().toList()
                    .map {
                        BlogDTO(
                            id = it[Blogs.id],
                            user = Users.fetchUser(it[Blogs.userId]) ?: Users.emptyUser(),
                            title = it[Blogs.title],
                            body = it[Blogs.body],
                            image = it[Blogs.image],
                            likes = it[Blogs.likes]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun fetchBlog(blogId: Int): BlogDTO? {
        return try {
            transaction {
                Blogs.select { Blogs.id.eq(blogId) }.firstOrNull()?.let {
                    BlogDTO(
                        id = it[Blogs.id],
                        user = Users.fetchUser(it[Blogs.userId]) ?: Users.emptyUser(),
                        title = it[Blogs.title],
                        body = it[Blogs.body],
                        image = it[Blogs.image],
                        likes = it[Blogs.likes]
                    )
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchUserBlogList(userId: String): List<BlogDTO> {
        return try {
            transaction {
                Blogs.select { Blogs.userId.eq(userId) }.toList()
                    .map {
                        BlogDTO(
                            id = it[Blogs.id],
                            user = Users.fetchUser(userId) ?: Users.emptyUser(),
                            title = it[Blogs.title],
                            body = it[Blogs.body],
                            image = it[Blogs.image],
                            likes = it[Blogs.likes]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}