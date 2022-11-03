package com.oriadesoftdev.retrofitrxjava3.data.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

class TaskResponse : ArrayList<TaskResponse.TaskResponseItem>(){
    @Keep
    data class TaskResponseItem(
        @SerializedName("id")
        val id: Long,
        @SerializedName("user_id")
        val userId: Int,
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