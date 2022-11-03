package com.oriadesoftdev.retrofitrxjava3.data.repository

import com.oriadesoftdev.retrofitrxjava3.data.remote.NetworkService
import com.oriadesoftdev.retrofitrxjava3.data.request.DeleteRequest
import com.oriadesoftdev.retrofitrxjava3.data.request.TaskRequest

class TaskRepository(private val networkService: NetworkService) {

    fun getAllTasks() = networkService.getAllTasks()
    fun addTask(taskRequest: TaskRequest) = networkService.addTask(taskRequest)
    fun updateTask(taskRequest: TaskRequest) = networkService.updateTask(taskRequest)
    fun deleteTask(taskRequest: DeleteRequest) = networkService.deleteTask(taskRequest)
}