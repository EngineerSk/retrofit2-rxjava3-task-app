package com.oriadesoftdev.retrofitrxjava3.data.repository.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.oriadesoftdev.retrofitrxjava3.data.paging.TaskPagingSource
import com.oriadesoftdev.retrofitrxjava3.data.paging.model.TaskPaging
import io.reactivex.rxjava3.core.Flowable

class TaskRepositoryPagingImpl(
    private val taskPagingSource: TaskPagingSource
) : TaskRepositoryPaging {
    override fun getAllTaskPaging(): Flowable<PagingData<TaskPaging.Task>> = Pager(
        config = defaultPagingConfig(),
        pagingSourceFactory = {
            taskPagingSource
        }
    ).flowable

    private fun defaultPagingConfig() = PagingConfig(
        pageSize = 10,
        prefetchDistance = 20,
        initialLoadSize = 30,
        maxSize = 50
    )
}