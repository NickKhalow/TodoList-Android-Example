package com.nickkhalow.testlist.domain.input

import com.nickkhalow.testlist.domain.input.storage.ItemInfoStorage
import com.nickkhalow.testlist.domain.list.TodoList

class DefaultItemInputField(
    private val todoList: TodoList,
    private val itemInfoStorage: ItemInfoStorage
) : ItemInputField {
    override fun update(text: String) {
        itemInfoStorage.update(text)
    }

    override fun update(done: Boolean) {
        itemInfoStorage.update(done)
    }

    override fun submit() {
        val text = itemInfoStorage.text()
        todoList.add(text)
    }

    override fun clear() {
        update("")
        update(false)
    }

}