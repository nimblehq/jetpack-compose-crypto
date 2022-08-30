package co.nimblehq.compose.crypto.data

import co.nimblehq.compose.crypto.data.model.response.ErrorResponse
import co.nimblehq.compose.crypto.data.service.JsonApiException
import co.nimblehq.compose.crypto.data.service.UnknownException
import co.nimblehq.compose.crypto.data.service.providers.MoshiBuilderProvider
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> flowTransform(block: suspend FlowCollector<T>.() -> Response<T>) = flow {
    val response = block()
    val body = response.body()
    if (response.isSuccessful && body != null) {
        emit(body)
    } else {
        throw mapError(response)
    }
}

private fun <T> mapError(response: Response<T>?): Exception {
    val errorBody = response?.errorBody()?.string()
    val errorResponse = parseErrorResponse(errorBody)

    return if (errorResponse != null && response != null) JsonApiException(errorResponse, response)
    else UnknownException
}

@Suppress("TooGenericExceptionCaught")
private fun parseErrorResponse(source: String?): ErrorResponse? {
    return try {
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val adapter = moshi.adapter(ErrorResponse::class.java)
        adapter.fromJson(source.orEmpty())
    } catch (e: Exception) {
        null
    }
}
