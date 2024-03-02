package com.nickkhalow.testlist.domain.async.list

import com.nickkhalow.testlist.domain.async.item.AsyncTodoItem
import com.nickkhalow.testlist.domain.async.item.WrapAsyncTodoItem
import com.nickkhalow.testlist.domain.list.TodoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class WrapAsyncTodoList(private val origin: TodoList) : AsyncTodoList {
    override suspend fun items(): List<AsyncTodoItem> {
        return coroutineScope {
            val result = async(Dispatchers.IO) {
                origin.items.map { WrapAsyncTodoItem(it) }
            }
            result.await()
        }
    }

    override suspend fun add(text: String) {
        return coroutineScope {
            val result = async(Dispatchers.IO) {
                origin.add(text)
            }
            result.await()
        }
    }

    override suspend fun remove(id: String) {
        return coroutineScope {
            val result = async(Dispatchers.IO) {
                origin.remove(id)
            }
            result.await()
        }
    }

    override fun onItemAdded(callback: (AsyncTodoItem) -> Unit) {
        origin.onItemAdded {
            callback(WrapAsyncTodoItem(it))
        }
    }
}