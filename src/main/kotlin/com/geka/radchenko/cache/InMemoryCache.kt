package com.geka.radchenko.cache

import data.register.RegisterRequestRemote

data class TokenCache(
    val login: String,
    val email: String,
    val token: String
)

data class UserSave(
    val id: Int,
    val login: String,
    val email: String,
    val password: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterRequestRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}