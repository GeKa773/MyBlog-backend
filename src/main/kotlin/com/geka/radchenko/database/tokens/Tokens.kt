package com.geka.radchenko.database.tokens

import com.geka.radchenko.utils.TokenCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table() {

    private val id = Tokens.varchar("id", 50)
    private val login = Tokens.varchar("login", 25)
    private val token = Tokens.varchar("token", 50)


    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.rowId
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchToken(myToken: String): TokenDTO? {
        return try {
            transaction {
                Tokens.select { Tokens.token.eq(myToken) }.firstOrNull()?.let {
                    TokenDTO(
                        rowId = it[Tokens.id],
                        login = it[Tokens.login],
                        token = it[Tokens.token],
                    )
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}