package com.nickkhalow.testlist.domain.item

interface TodoItem {
    val id: String
    val text: String
    val done: Boolean

    fun finish()
}