package com.oriadesoftdev.retrofitrxjava3.data.remote

import com.oriadesoftdev.retrofitrxjava3.data.request.DeleteRequest
import com.oriadesoftdev.retrofitrxjava3.data.request.TaskRequest
import com.oriadesoftdev.retrofitrxjava3.data.response.TaskResponse
import com.oriadesoftdev.retrofitrxjava3.data.response.TaskResponsePaging
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

    @Headers(Endpoint.HEADER_ACCEPT)
    @GET("${Endpoint.SEARCH_TASK}{query}")
    fun searchTask(@Path("query") query: String): Single<TaskResponse>

    @Headers(Endpoint.HEADER_ACCEPT)
    @GET(Endpoint.GET_ALL_TASK_PAGING)
    fun getAllTaskPaging(@Query("page") pageNumber:Int): Single<TaskResponsePaging>
}