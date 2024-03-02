package com.nickkhalow.testlist.domain.input

interface ItemInputField {
    fun update(text: String)

    fun update(done: Boolean)

    suspend fun submit()

    fun clear()
}