package com.nickkhalow.testlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nickkhalow.testlist.ui.input.InputField
import com.nickkhalow.testlist.ui.todolist.TodoListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = (application as MainApplication)
        TodoListAdapter(app.todoList, this, findViewById(R.id.recycle_view))
        InputField(app.inputField, app.itemInfoStorage, CoroutineScope(Dispatchers.Main), this)
    }
}