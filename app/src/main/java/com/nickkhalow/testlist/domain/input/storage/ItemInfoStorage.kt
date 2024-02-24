package com.nickkhalow.testlist.domain.input.storage

interface ItemInfoStorage {

    fun update(text: String)

    fun update(done: Boolean)

    fun text(): String

    fun done(): Boolean
}