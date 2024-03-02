package com.nickkhalow.testlist.ui.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nickkhalow.testlist.R
import com.nickkhalow.testlist.domain.async.item.AsyncTodoItem
import com.nickkhalow.testlist.domain.async.list.AsyncTodoList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListAdapter(private val todoList: AsyncTodoList, private val scope: CoroutineScope) :
    RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    private var currentCount: Int = 0

    init {
        updateCountAndNotifySet()
        todoList.onItemAdded {
            val self = this
            scope.launch {
                val list = todoList.items()
                currentCount = list.size
                val searching = it
                val index = list.indexOfFirst { searching.id == it.id }
                self.notifyItemInserted(index)
            }
        }
    }

    constructor(todoList: AsyncTodoList, context: Context, recyclerView: RecyclerView) : this(
        todoList, CoroutineScope(Dispatchers.Main)
    ) {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_row_item, parent, false)
        Log.d("TodoListAdapter", "onCreateViewHolder: success")
        return ViewHolder(this, todoList, scope, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TodoListAdapter", "onBindViewHolder")
        scope.launch {
            holder.bind(todoList.items()[position])
        }
    }

    override fun getItemCount(): Int {
        val count = currentCount
        Log.d("TodoListAdapter", "getItemCount: $count")
        return count
    }

    suspend fun updateCount() {
        currentCount = todoList.items().size
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateCountAndNotifySet() {
        val self = this
        scope.launch {
            updateCount()
            self.notifyDataSetChanged()
        }
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(
        private val adapter: TodoListAdapter,
        private val list: AsyncTodoList,
        private val scope: CoroutineScope,
        view: View
    ) :
        RecyclerView.ViewHolder(view) {
        private val textView: TextView
        private val flagView: ToggleButton
        private val deleteButton: Button
        private val progressBar: ProgressBar

        init {
            textView = view.findViewById(R.id.textView)
            flagView = view.findViewById(R.id.toggle)
            deleteButton = view.findViewById(R.id.delete)
            progressBar = view.findViewById(R.id.progress)
        }

        suspend fun bind(item: AsyncTodoItem) {
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.INVISIBLE

            textView.text = item.text()
            flagView.isChecked = item.done()
            flagView.isEnabled = item.done() == false

            progressBar.visibility = View.INVISIBLE
            textView.visibility = View.VISIBLE

            flagView.setOnCheckedChangeListener { _, _ ->
                scope.launch {
                    item.finish()
                    flagView.isChecked = item.done()
                    flagView.isEnabled = item.done() == false
                }
            }

            deleteButton.setOnClickListener {
                scope.launch {
                    val index = list.items().indexOfFirst { it.id == item.id }
                    adapter.todoList.remove(item.id)
                    adapter.updateCount()
                    adapter.notifyItemRemoved(index)
                }
            }
        }
    }
}