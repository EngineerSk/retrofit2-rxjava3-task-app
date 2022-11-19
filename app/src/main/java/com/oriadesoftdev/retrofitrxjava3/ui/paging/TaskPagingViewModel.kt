package com.oriadesoftdev.retrofitrxjava3.ui.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.oriadesoftdev.retrofitrxjava3.data.paging.model.TaskPaging
import com.oriadesoftdev.retrofitrxjava3.data.repository.paging.TaskRepositoryPaging
import com.oriadesoftdev.retrofitrxjava3.data.repository.paging.TaskRepositoryPagingImpl
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TaskPagingViewModel(
    private val repositoryPaging: TaskRepositoryPagingImpl
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllTaskPaging(): Flowable<PagingData<TaskPaging.Task>> {
        return repositoryPaging.getAllTaskPaging().cachedIn(viewModelScope)
    }
}