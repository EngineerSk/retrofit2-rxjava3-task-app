package com.oriadesoftdev.retrofitrxjava3.data.paging.model

import com.google.gson.annotations.SerializedName

data class TaskPaging(
    val totalPage: Int = 0,
    val currentPage: Int = 0,
    val tasks: List<Task>
) {
    val lastPage: Int = totalPage

    data class Task(
        @SerializedName("id")
        val id: String,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("body")
        val body: String,
        @SerializedName("note")
        val note: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("updated_at")
        val updatedAt: String
    )
}