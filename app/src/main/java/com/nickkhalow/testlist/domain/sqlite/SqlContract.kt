package com.nickkhalow.testlist.domain.sqlite

import android.provider.BaseColumns

object SqlContract {
    const val TABLE_NAME = "list"
    const val COLUMN_ID = BaseColumns._ID
    const val COLUMN_NAME_TEXT = "text"
    const val COLUMN_NAME_DONE = "done"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_TEXT TEXT," +
                "$COLUMN_NAME_DONE INTEGER)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}