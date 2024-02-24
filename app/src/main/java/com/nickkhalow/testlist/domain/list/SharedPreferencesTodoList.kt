package com.nickkhalow.testlist.domain.list

import android.content.SharedPreferences
import com.nickkhalow.testlist.domain.item.SharedPreferencesTodoItem
import com.nickkhalow.testlist.domain.item.TodoItem

class SharedPreferencesTodoList(private val preferences: SharedPreferences) : TodoList {
    private var callback: ((TodoItem) -> Unit)? = null

    override val items: List<TodoItem>
        get() = idSet().map { SharedPreferencesTodoItem(preferences, it) }

    override fun add(text: String) {
        val set = idSet()
        val id = "${idSet().size + 1}"
        set.add(id)

        preferences.edit()
            .putStringSet("idList", set)
            .putString("$id:text", text)
            .apply()

        callback?.invoke(SharedPreferencesTodoItem(preferences, id))
    }

    override fun remove(id: String) {
        val set = idSet()
        set.remove(id)
        preferences.edit()
            .putStringSet("idList", set)
            .remove("$id:text")
            .remove("$id:done")
            .apply()
    }

    private fun idSet(): MutableSet<String> {
        val loaded = preferences.getStringSet("idList", mutableSetOf())
        return loaded!!
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