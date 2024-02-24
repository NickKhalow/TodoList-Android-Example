package com.nickkhalow.testlist.ui.todolist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nickkhalow.testlist.R
import com.nickkhalow.testlist.domain.item.TodoItem
import com.nickkhalow.testlist.domain.list.TodoList

class TodoListAdapter(private val todoList: TodoList) :
    RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    init {
        todoList.onItemAdded { this.notifyDataSetChanged() } //can be optimized
    }

    constructor(todoList: TodoList, context: Context, recyclerView: RecyclerView) : this(todoList) {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_row_item, parent, false)
        Log.d("TodoListAdapter", "onCreateViewHolder: success")
        return ViewHolder(this, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TodoListAdapter", "onBindViewHolder")
        holder.bind(position, todoList.items[position])
    }

    override fun getItemCount(): Int {
        val count = todoList.items.size
        Log.d("TodoListAdapter", "getItemCount: $count")
        return count
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(private val adapter: TodoListAdapter, view: View) :
        RecyclerView.ViewHolder(view) {
        private val textView: TextView
        private val flagView: ToggleButton
        private val deleteButton: Button

        init {
            textView = view.findViewById(R.id.textView)
            flagView = view.findViewById(R.id.toggle)
            deleteButton = view.findViewById(R.id.delete)
        }

        fun bind(index: Int, item: TodoItem) {
            textView.text = item.text
            flagView.isChecked = item.done
            flagView.isEnabled = item.done == false
            flagView.setOnCheckedChangeListener { _, _ ->
                run {
                    item.finish()
                    flagView.isChecked = item.done
                    flagView.isEnabled = item.done == false
                }
            }

            deleteButton.setOnClickListener {
                adapter.todoList.remove(item.id)
                adapter.notifyItemRemoved(index)
            }
        }
    }
}