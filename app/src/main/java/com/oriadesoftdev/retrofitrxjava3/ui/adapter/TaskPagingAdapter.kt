package com.oriadesoftdev.retrofitrxjava3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oriadesoftdev.retrofitrxjava3.data.paging.model.TaskPaging
import com.oriadesoftdev.retrofitrxjava3.databinding.TaskListItemBinding

class TaskPagingAdapter :
    PagingDataAdapter<TaskPaging.Task, TaskPagingAdapter.TaskPagingViewHolder>(
        diffCallback = object : DiffUtil.ItemCallback<TaskPaging.Task>() {
            override fun areItemsTheSame(
                oldItem: TaskPaging.Task,
                newItem: TaskPaging.Task
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TaskPaging.Task,
                newItem: TaskPaging.Task
            ): Boolean = oldItem == newItem

        }
    ) {

    override fun onBindViewHolder(holder: TaskPagingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskPagingViewHolder =
        TaskPagingViewHolder(
            TaskListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    class TaskPagingViewHolder(private val binding: TaskListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: TaskPaging.Task) {
            binding.apply {
                idTextView.text =
                    data.id.toString()
                bodyTextView.text = data.body
                noteTextView.text = data.note
                titleTextView.text = data.title
                statusTextView.text = data.status
//                taskEditButton.setOnClickListener {
//                    taskEditClickListener(data)
//                }
//                findViewById<ShapeableImageView>(R.id.taskDeleteButton).setOnClickListener {
//                    taskDeleteClickListener(data)
//                }
            }
        }
    }


}