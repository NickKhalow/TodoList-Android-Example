package com.nickkhalow.testlist.domain.input.storage

import android.content.SharedPreferences

class SharedPreferencesItemInfoStorage(private val preferences: SharedPreferences) :
    ItemInfoStorage {

    private val textKey = "current-text"
    private val doneKey = "current-done"

    override fun update(text: String) {
        preferences.edit().putString(textKey, text).apply()
    }

    override fun update(done: Boolean) {
        preferences.edit().putBoolean(doneKey, done).apply()
    }

    override fun text(): String {
        return preferences.getString(textKey, "") as String
    }

    override fun done(): Boolean {
        return preferences.getBoolean(doneKey, false)
    }
}