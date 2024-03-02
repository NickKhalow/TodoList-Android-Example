package com.nickkhalow.testlist.domain.async.list

import com.nickkhalow.testlist.domain.async.item.AsyncTodoItem

interface AsyncTodoList {
    suspend fun items(): List<AsyncTodoItem>

    suspend fun add(text: String)

    suspend fun remove(id: String)

    fun onItemAdded(callback: (AsyncTodoItem) -> Unit)
}