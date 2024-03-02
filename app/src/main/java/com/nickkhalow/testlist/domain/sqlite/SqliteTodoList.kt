package com.nickkhalow.testlist.domain.sqlite

import android.content.ContentValues
import com.nickkhalow.testlist.domain.item.TodoItem
import com.nickkhalow.testlist.domain.list.TodoList

//TODO can be launched in a background thread to optimise the main thread for drawing
class SqliteTodoList(private val connection: SqliteDbConnection) : TodoList {
    private var callback: ((TodoItem) -> Unit)? = null

    override val items: List<TodoItem>
        get() {
            val db = connection.readableDatabase
            val projection = arrayOf(SqlContract.COLUMN_ID)
            val cursor = db.query(
                SqlContract.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            val list = mutableListOf<TodoItem>()

            while (cursor.moveToNext()) {
                val index = cursor.getColumnIndex(SqlContract.COLUMN_ID)
                val id = cursor.getLong(index)
                list.add(SqliteTodoItem(connection, id))
            }
            cursor.close()
            return list
        }

    override fun add(text: String) {
        val db = connection.writableDatabase

        val values = ContentValues()
        values.put(SqlContract.COLUMN_NAME_TEXT, text)
        values.put(SqlContract.COLUMN_NAME_DONE, 0)

        val id = db.insert(SqlContract.TABLE_NAME, null, values)
        callback?.invoke(SqliteTodoItem(connection, id))
    }

    override fun remove(id: String) {
        val db = connection.writableDatabase
        db.delete(SqlContract.TABLE_NAME, "${SqlContract.COLUMN_ID} = ?", arrayOf(id))
    }

    override fun onItemAdded(callback: (TodoItem) -> Unit) {
        this.callback = callback
    }
}