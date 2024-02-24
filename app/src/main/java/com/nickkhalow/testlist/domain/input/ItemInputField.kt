package com.nickkhalow.testlist.domain.input

interface ItemInputField {
    fun update(text: String)

    fun update(done: Boolean)

    fun submit()

    fun clear()
}