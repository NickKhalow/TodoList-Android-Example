package com.nickkhalow.testlist.domain.list

import com.nickkhalow.testlist.domain.item.CachedTodoItem
import com.nickkhalow.testlist.domain.item.TodoItem

class CachedTodoList(private val origin: TodoList, private val cache: MutableList<TodoItem>) :
    TodoList {
    override val items: List<TodoItem>
        get() {
            if (cache.isEmpty()) {
                val cachedList = origin.items.map { CachedTodoItem(it) }
                cache.addAll(cachedList)
            }
            return cache
        }

    override fun add(text: String) {
        origin.add(text)
        cache.clear()
    }

    override fun remove(id: String) {
        origin.remove(id)
        cache.clear()
    }

    override fun onItemAdded(callback: (TodoItem) -> Unit) {
        origin.onItemAdded(callback)
    }
}