package com.nickkhalow.testlist.domain.input.storage

class MemoryItemInfoStorage : ItemInfoStorage {
    private var text = ""
    private var done = false

    override fun update(text: String) {
        this.text = text
    }

    override fun update(done: Boolean) {
        this.done = done
    }

    override fun text(): String {
        return text
    }

    override fun done(): Boolean {
        return done
    }
}