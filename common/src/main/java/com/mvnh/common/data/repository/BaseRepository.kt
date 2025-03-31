package com.mvnh.common.data.repository

import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {
    protected suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<T>,
        transform: (T) -> R
    ): R {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let(transform) ?: throw IllegalStateException("Empty response body: ${response.errorBody()?.string()}")
            } else {
                throw Exception("API error: ${response.errorBody()?.string()}")
            }
        } catch (e: IOException) {
            throw IOException("Network error", e)
        } catch (e: Exception) {
            throw Exception("Unknown error", e)
        }
    }
}
