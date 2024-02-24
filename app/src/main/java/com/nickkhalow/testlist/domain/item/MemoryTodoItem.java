package com.nickkhalow.testlist.domain.item;

import androidx.annotation.NonNull;

public class MemoryTodoItem implements TodoItem {
    private final String id;
    private final String text;
    private boolean done;

    public MemoryTodoItem(String id, String text) {
        this(id, text, false);
    }

    public MemoryTodoItem(String id, String text, boolean done) {
        this.id = id;
        this.text = text;
        this.done = done;
    }

    @NonNull
    @Override
    public String getId() {
        return id;
    }

    @NonNull
    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean getDone() {
        return done;
    }

    @Override
    public void finish() {
        done = true;
    }
}
