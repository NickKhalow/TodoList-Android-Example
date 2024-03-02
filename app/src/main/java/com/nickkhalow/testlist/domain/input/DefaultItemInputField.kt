package com.nickkhalow.testlist.domain.input

import com.nickkhalow.testlist.domain.async.list.AsyncTodoList
import com.nickkhalow.testlist.domain.input.storage.ItemInfoStorage

class DefaultItemInputField(
    private val todoList: AsyncTodoList,
    private val itemInfoStorage: ItemInfoStorage
) : ItemInputField {
    override fun update(text: String) {
        itemInfoStorage.update(text)
    }

    override fun update(done: Boolean) {
        itemInfoStorage.update(done)
    }

    override suspend fun submit() {
        val text = itemInfoStorage.text()
        todoList.add(text)
    }

    override fun clear() {
        update("")
        update(false)
    }

}