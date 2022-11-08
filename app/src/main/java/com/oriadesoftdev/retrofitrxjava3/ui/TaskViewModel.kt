package com.oriadesoftdev.retrofitrxjava3.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.oriadesoftdev.retrofitrxjava3.MyApplication
import com.oriadesoftdev.retrofitrxjava3.data.repository.TaskRepository
import com.oriadesoftdev.retrofitrxjava3.data.request.DeleteRequest
import com.oriadesoftdev.retrofitrxjava3.data.request.TaskRequest
import com.oriadesoftdev.retrofitrxjava3.data.response.TaskResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val taskRepository: TaskRepository =
        TaskRepository((application as MyApplication).networkService)

    val taskIdLiveData = MutableLiveData<Long>()
    val taskUserIdLiveData = MutableLiveData<Int>()
    val taskTitleLiveData = MutableLiveData<String>()
    val taskBodyLiveData = MutableLiveData<String>()
    val taskStatusLiveData = MutableLiveData<String>()
    val taskNoteLiveData = MutableLiveData<String>()

    val errorLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val isSuccessLiveData = MutableLiveData<Boolean>()
    val taskListLiveData = MutableLiveData<ArrayList<TaskResponse.TaskResponseItem>>()

    companion object {
        private const val TAG = "TaskViewModel"
    }

    init {
        getAllTasks()
    }

    fun getAllTasks() {
        isLoadingLiveData.value = true
        compositeDisposable.add(
            taskRepository.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isLoadingLiveData.value = false
                    taskListLiveData.value = it
                    isSuccessLiveData.value = true
                    taskListLiveData.value?.forEach { taskItem ->
                        Log.d(TAG, "getAllTasks: $taskItem")
                    }
                }) {
                    errorLiveData.value = it.message
                }
        )
    }

    fun addTask() {
        isLoadingLiveData.value = true
        compositeDisposable.add(
            taskRepository.addTask(createTaskRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isLoadingLiveData.value = false
                    isSuccessLiveData.value = true
                    Log.d(TAG, "addTask: $it")
                    getAllTasks()
                }) {
                    errorLiveData.value = it.message
                    Log.e(TAG, "addTask: ${it.message}")
                    isLoadingLiveData.value = false
                }
        )
    }

    private fun createTaskRequest() = TaskRequest(
        userId = taskUserIdLiveData.value?.toInt() ?: 0,
        title = taskTitleLiveData.value.toString(),
        body = taskBodyLiveData.value.toString(),
        note = taskNoteLiveData.value.toString(),
        status = taskStatusLiveData.value.toString()
    )

    private fun createEditTaskRequest() = TaskRequest(
        id = taskIdLiveData.value?.toLong() ?: 0L,
        userId = taskUserIdLiveData.value?.toInt() ?: 0,
        title = taskTitleLiveData.value.toString(),
        body = taskBodyLiveData.value.toString(),
        note = taskNoteLiveData.value.toString(),
        status = taskStatusLiveData.value.toString()
    )

    fun searchTask(query: String): Single<TaskResponse> {
        return taskRepository.searchTask(query)
    }

    fun editTask() {
        isLoadingLiveData.value = true
        compositeDisposable.add(
            taskRepository.updateTask(createEditTaskRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isLoadingLiveData.value = false
                    isSuccessLiveData.value = true
                    Log.d(TAG, "addTask: $it")
                    getAllTasks()
                }) {
                    errorLiveData.value = it.message
                    Log.e(TAG, "addTask: ${it.message}")
                    isLoadingLiveData.value = false
                }
        )
    }

    fun deleteTask() {
        isLoadingLiveData.value = true
        compositeDisposable.add(
            taskRepository.deleteTask(createDeleteTaskRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isLoadingLiveData.value = false
                    isSuccessLiveData.value = true
                    Log.d(TAG, "delete: $it")
                    getAllTasks()
                }) {
                    errorLiveData.value = it.message
                    Log.e(TAG, "delete: ${it.message}")
                    isLoadingLiveData.value = false
                }
        )
    }

    private fun createDeleteTaskRequest() = DeleteRequest(
        id = taskIdLiveData.value?.toLong() ?: 0L,
        userId = taskUserIdLiveData.value?.toInt() ?: 0
    )

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}