package com.nickkhalow.testlist

import android.app.Application
import android.content.Context
import com.nickkhalow.testlist.domain.async.list.AsyncTodoList
import com.nickkhalow.testlist.domain.async.list.WrapAsyncTodoList
import com.nickkhalow.testlist.domain.input.DefaultItemInputField
import com.nickkhalow.testlist.domain.input.ItemInputField
import com.nickkhalow.testlist.domain.input.storage.ItemInfoStorage
import com.nickkhalow.testlist.domain.input.storage.SharedPreferencesItemInfoStorage
import com.nickkhalow.testlist.domain.list.CachedTodoList
import com.nickkhalow.testlist.domain.sqlite.SqliteTodoList
import com.nickkhalow.testlist.domain.sqlite.SqliteDbConnection

class MainApplication : Application() {
    lateinit var connection: SqliteDbConnection
    lateinit var todoList: AsyncTodoList
    lateinit var itemInfoStorage: ItemInfoStorage
    lateinit var inputField: ItemInputField

    override fun onCreate() {
        super.onCreate()
        val preferences = getSharedPreferences("todo-list", Context.MODE_PRIVATE)

        connection = SqliteDbConnection(this)

        todoList = WrapAsyncTodoList(
            CachedTodoList(
                SqliteTodoList(connection),
                mutableListOf()
            )
        )
        itemInfoStorage = SharedPreferencesItemInfoStorage(preferences)
        inputField = DefaultItemInputField(todoList, itemInfoStorage)
    }
}