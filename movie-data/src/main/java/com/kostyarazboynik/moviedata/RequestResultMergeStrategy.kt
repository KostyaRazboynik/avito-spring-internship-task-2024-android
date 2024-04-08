package com.kostyarazboynik.moviedata

import com.kostyarazboynik.domain.data.MergeStrategy
import com.kostyarazboynik.domain.model.RequestResult

internal class RequestResultMergeStrategy<T : Any> : MergeStrategy<RequestResult<T>> {

    override fun merge(left: RequestResult<T>, right: RequestResult<T>): RequestResult<T> {
        return when {
            left is RequestResult.Success && right is RequestResult.Success -> merge(left, right)
            left is RequestResult.Success && right is RequestResult.InProgress -> merge(left, right)
            left is RequestResult.Success && right is RequestResult.Error -> merge(left, right)
            left is RequestResult.InProgress && right is RequestResult.Success -> merge(left, right)
            left is RequestResult.InProgress && right is RequestResult.InProgress ->
                merge(left, right)

            left is RequestResult.InProgress && right is RequestResult.Error -> merge(left, right)
            left is RequestResult.Error && right is RequestResult.Success -> merge(left, right)
            left is RequestResult.Error && right is RequestResult.InProgress -> merge(left, right)
            left is RequestResult.Error && right is RequestResult.Error -> merge(left, right)
            else -> error("Unreachable brunch")
        }
    }

    private fun merge(
        cache: RequestResult.Success<T>,
        server: RequestResult.Success<T>,
    ): RequestResult<T> = RequestResult.Success(server.data)

    private fun merge(
        cache: RequestResult.Success<T>,
        server: RequestResult.InProgress<T>,
    ): RequestResult<T> = RequestResult.InProgress(cache.data)

    private fun merge(
        cache: RequestResult.Success<T>,
        server: RequestResult.Error<T>,
    ): RequestResult<T> = RequestResult.Error(data = cache.data, error = server.error)

    private fun merge(
        cache: RequestResult.InProgress<T>,
        server: RequestResult.Success<T>,
    ): RequestResult<T> = RequestResult.InProgress(server.data)

    private fun merge(
        cache: RequestResult.InProgress<T>,
        server: RequestResult.InProgress<T>,
    ): RequestResult<T> =
        when {
            cache.data != null -> RequestResult.InProgress(cache.data)
            else -> RequestResult.InProgress(server.data)
        }

    private fun merge(
        cache: RequestResult.InProgress<T>,
        server: RequestResult.Error<T>,
    ): RequestResult<T> =
        RequestResult.Error(data = server.data ?: cache.data, error = server.error)

    private fun merge(
        cache: RequestResult.Error<T>,
        server: RequestResult.Success<T>,
    ): RequestResult<T> = RequestResult.Error(data = server.data, error = cache.error)

    private fun merge(
        cache: RequestResult.Error<T>,
        server: RequestResult.InProgress<T>,
    ): RequestResult<T> =
        RequestResult.Error(data = server.data ?: cache.data, error = cache.error)

    private fun merge(
        cache: RequestResult.Error<T>,
        server: RequestResult.Error<T>,
    ): RequestResult<T> =
        RequestResult.Error(data = server.data ?: cache.data, error = server.error ?: cache.error)
}
