package com.nickkhalow.testlist.domain.item

class CachedTodoItem(
    private val origin: TodoItem,
    private var cachedText: String? = null,
    private var cachedDone: Boolean? = null
) : TodoItem {
    override val id: String
        get() = origin.id
    override val text: String
        get() {
            if (cachedText == null) {
                cachedText = origin.text
            }
            return cachedText as String
        }
    override val done: Boolean
        get() {
            if (cachedDone == null) {
                cachedDone = origin.done
            }
            return cachedDone as Boolean
        }

    override fun finish() {
        origin.finish()
        cachedDone = null
    }
}