package com.nickkhalow.testlist.domain.sqlite

import android.content.ContentValues
import com.nickkhalow.testlist.domain.item.TodoItem

class SqliteTodoItem(
    private val connection: SqliteDbConnection,
    private val itemId: Long
) : TodoItem {

    private val idSelection: String = "${SqlContract.COLUMN_ID} = ?"
    private val selectionArgs = arrayOf(id)

    override val id: String
        get() = itemId.toString()
    override val text: String
        get() {
            val db = connection.readableDatabase
            val projection = arrayOf(SqlContract.COLUMN_NAME_TEXT)
            val cursor = db.query(
                SqlContract.TABLE_NAME,
                projection,
                idSelection,
                selectionArgs,
                null,
                null,
                null
            )

            if (cursor.moveToNext()) {
                val index = cursor.getColumnIndex(SqlContract.COLUMN_NAME_TEXT)
                val content = cursor.getString(index)
                cursor.close()
                return content
            }

            throw Exception("Text by id $id not found")
        }
    override val done: Boolean
        get() {
            val db = connection.readableDatabase
            val projection = arrayOf(SqlContract.COLUMN_NAME_DONE)
            val cursor = db.query(
                SqlContract.TABLE_NAME,
                projection,
                idSelection,
                selectionArgs,
                null,
                null,
                null
            )

            if (cursor.moveToNext()) {
                val index = cursor.getColumnIndex(SqlContract.COLUMN_NAME_DONE)
                val content = cursor.getInt(index) != 0
                cursor.close()
                return content
            }

            throw Exception("Done by id $id not found")
        }

    override fun finish() {
        val db = connection.writableDatabase

        val values = ContentValues()
        values.put(SqlContract.COLUMN_NAME_DONE, 1)

        db.update(
            SqlContract.TABLE_NAME,
            values,
            "${SqlContract.COLUMN_ID} = ?",
            arrayOf(id)
        )
    }
}