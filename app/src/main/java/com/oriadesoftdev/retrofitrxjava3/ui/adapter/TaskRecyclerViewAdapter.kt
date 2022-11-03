package com.oriadesoftdev.retrofitrxjava3.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.oriadesoftdev.retrofitrxjava3.R
import com.oriadesoftdev.retrofitrxjava3.data.response.TaskResponse

class TaskRecyclerViewAdapter(
    private val taskEditClickListener: (TaskResponse.TaskResponseItem) -> Unit,
    private val taskDeleteClickListener: (TaskResponse.TaskResponseItem) -> Unit
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<TaskResponse.TaskResponseItem>() {
        override fun areItemsTheSame(
            oldItem: TaskResponse.TaskResponseItem,
            newItem: TaskResponse.TaskResponseItem
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: TaskResponse.TaskResponseItem,
            newItem: TaskResponse.TaskResponseItem
        ) = oldItem == newItem

    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            taskEditClickListener,
            taskDeleteClickListener,
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.task_list_item,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount() = asyncListDiffer.currentList.size

    fun setTaskList(taskResponse: ArrayList<TaskResponse.TaskResponseItem>) {
        asyncListDiffer.submitList(taskResponse)
    }

    class TaskViewHolder(
        private val taskEditClickListener: (TaskResponse.TaskResponseItem) -> Unit,
        private val taskDeleteClickListener: (TaskResponse.TaskResponseItem) -> Unit, itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskResponseItem: TaskResponse.TaskResponseItem) {
            itemView.apply {
                findViewById<MaterialTextView>(R.id.idTextView).text =
                    taskResponseItem.id.toString()
                findViewById<MaterialTextView>(R.id.bodyTextView).text = taskResponseItem.body
                findViewById<MaterialTextView>(R.id.noteTextView).text = taskResponseItem.note
                findViewById<MaterialTextView>(R.id.titleTextView).text = taskResponseItem.title
                findViewById<MaterialTextView>(R.id.statusTextView).text = taskResponseItem.status
                findViewById<ShapeableImageView>(R.id.taskEditButton).setOnClickListener {
                    taskEditClickListener(taskResponseItem)
                }
                findViewById<ShapeableImageView>(R.id.taskDeleteButton).setOnClickListener {
                    taskDeleteClickListener(taskResponseItem)
                }
            }
        }

    }
}