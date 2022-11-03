package com.oriadesoftdev.retrofitrxjava3.data.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeleteRequest(
    @SerializedName(value = "id")
    val id: Long,
    @SerializedName(value = "user_id")
    val userId: Int,
)
