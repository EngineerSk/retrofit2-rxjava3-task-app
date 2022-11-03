package com.oriadesoftdev.retrofitrxjava3.data.remote

import com.oriadesoftdev.retrofitrxjava3.data.request.DeleteRequest
import com.oriadesoftdev.retrofitrxjava3.data.request.TaskRequest
import com.oriadesoftdev.retrofitrxjava3.data.response.TaskResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface NetworkService {

    @Headers(Endpoint.HEADER_ACCEPT)
    @GET(Endpoint.GET_ALL_TASK)
    fun getAllTasks(): Single<TaskResponse>

    @Headers(Endpoint.HEADER_ACCEPT)
    @POST(Endpoint.ADD_TASK)
    fun addTask(@Body taskRequest: TaskRequest): Single<TaskResponse.TaskResponseItem>

    @Headers(Endpoint.HEADER_ACCEPT)
    @POST(Endpoint.EDIT_TASK)
    fun updateTask(@Body taskRequest: TaskRequest): Single<TaskResponse.TaskResponseItem>

    @Headers(Endpoint.HEADER_ACCEPT)
    @POST(Endpoint.DELETE_TASK)
    fun deleteTask(@Body taskRequest: DeleteRequest): Single<TaskResponse.TaskResponseItem>
}