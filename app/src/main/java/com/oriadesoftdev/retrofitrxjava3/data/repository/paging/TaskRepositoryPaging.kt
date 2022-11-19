package com.oriadesoftdev.retrofitrxjava3.data.repository.paging

import androidx.paging.PagingData
import com.oriadesoftdev.retrofitrxjava3.data.paging.model.TaskPaging
import io.reactivex.rxjava3.core.Flowable

interface TaskRepositoryPaging {

    fun getAllTaskPaging():Flowable<PagingData<TaskPaging.Task>>
}