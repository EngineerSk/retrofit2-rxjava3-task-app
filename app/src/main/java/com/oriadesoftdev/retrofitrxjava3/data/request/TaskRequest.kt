package com.oriadesoftdev.retrofitrxjava3.data.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TaskRequest(
    @SerializedName(value = "id")
    val id: Long = 0L,

    @SerializedName(value = "user_id")
    val userId: Int,

    @SerializedName(value = "title")
    val title: String,

    @SerializedName(value = "body")
    val body: String,

    @SerializedName(value = "note")
    val note: String,

    @SerializedName(value = "status")
    val status: String
)
