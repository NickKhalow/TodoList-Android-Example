package com.nickkhalow.testlist.domain.list

import com.nickkhalow.testlist.domain.item.MemoryTodoItem
import com.nickkhalow.testlist.domain.item.TodoItem

class MemoryTodoList(private val list: MutableList<TodoItem>) : TodoList {
    private var currentId = 0
    private var callback: ((TodoItem) -> Unit)? = null

    constructor() : this(mutableListOf())

    override val items: List<TodoItem>
        get() = list

    override fun add(text: String) {
        val nextId = currentId++
        val item: TodoItem = MemoryTodoItem(
            nextId.toString(),
            text
        )
        list.add(item)
        callback?.invoke(item)
    }

    override fun remove(id: String) {
        list.removeAll { it.id == id }
    }

    override fun onItemAdded(callback: (TodoItem) -> Unit) {
        if (this.callback == null) {
            this.callback = callback
            return
        }
        val previousCallback = this.callback as (TodoItem) -> Unit
        this.callback = {
            previousCallback(it)
            callback(it)
        }
    }
}