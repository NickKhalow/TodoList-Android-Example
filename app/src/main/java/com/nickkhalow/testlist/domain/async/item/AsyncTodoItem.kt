package com.nickkhalow.testlist.domain.async.item

interface AsyncTodoItem {
    val id: String
    suspend fun text(): String
    suspend fun done(): Boolean

    suspend fun finish()
}