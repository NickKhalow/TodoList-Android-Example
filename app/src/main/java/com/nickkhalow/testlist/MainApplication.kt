package com.nickkhalow.testlist

import android.app.Application
import android.content.Context
import com.nickkhalow.testlist.domain.input.DefaultItemInputField
import com.nickkhalow.testlist.domain.input.ItemInputField
import com.nickkhalow.testlist.domain.input.storage.ItemInfoStorage
import com.nickkhalow.testlist.domain.input.storage.SharedPreferencesItemInfoStorage
import com.nickkhalow.testlist.domain.list.SharedPreferencesTodoList
import com.nickkhalow.testlist.domain.list.TodoList

class MainApplication : Application() {
    lateinit var todoList: TodoList
    lateinit var itemInfoStorage: ItemInfoStorage
    lateinit var inputField: ItemInputField

    override fun onCreate() {
        super.onCreate()
        val preferences = getSharedPreferences("todo-list", Context.MODE_PRIVATE)
        todoList = SharedPreferencesTodoList(preferences)
        itemInfoStorage = SharedPreferencesItemInfoStorage(preferences)
        inputField = DefaultItemInputField(todoList, itemInfoStorage)
    }
}