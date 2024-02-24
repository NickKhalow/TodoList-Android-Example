package com.nickkhalow.testlist.domain.list

import com.nickkhalow.testlist.domain.item.TodoItem

interface TodoList {
    val items: List<TodoItem>

    fun add(text: String)

    fun remove(id: String)

    fun onItemAdded(callback: (TodoItem) -> Unit)
}