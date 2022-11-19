package com.oriadesoftdev.retrofitrxjava3.data.paging.mapper

interface TaskPagingResponseMapper<Response, Model> {

    fun mapFromResponse(response: Response): Model
}