package com.nickkhalow.testlist.domain.async.item

import com.nickkhalow.testlist.domain.item.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class WrapAsyncTodoItem(private val origin: TodoItem) : AsyncTodoItem {
    override val id: String
        get() = origin.id

    override suspend fun text(): String {
        return coroutineScope {
            val result = async(Dispatchers.IO) {
                origin.text
            }
            result.await()
        }
    }

    override suspend fun done(): Boolean {
        return coroutineScope {
            val result = async(Dispatchers.IO) {
                origin.done
            }
            result.await()
        }
    }

    override suspend fun finish() {
        return coroutineScope {
            val result = async(Dispatchers.IO) {
                origin.finish()
            }
            result.await()
        }
    }
}