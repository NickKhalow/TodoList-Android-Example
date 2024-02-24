package com.nickkhalow.testlist.domain.item

import android.content.SharedPreferences

class SharedPreferencesTodoItem(
    private val preferences: SharedPreferences,
    override val id: String
) : TodoItem {
    private val doneKey = "$id:done"

    override val text: String
        get() = preferences.getString("$id:text", "") as String

    override val done: Boolean
        get() = preferences.getBoolean(doneKey, false)

    override fun finish() {
        val edit = preferences.edit()
        edit.putBoolean(doneKey, true)
        edit.apply()
    }
}