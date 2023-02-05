package com.geka.radchenko.database.blogs

import com.geka.radchenko.database.users.UserDTO

data class BlogDTO(
    val id: Int,
    val user: UserDTO,
    val title: String,
    val body: String?,
    val image: String?,
    val likes: Int,
)
