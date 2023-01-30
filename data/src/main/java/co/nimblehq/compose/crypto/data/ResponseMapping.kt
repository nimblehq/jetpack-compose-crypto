package co.nimblehq.compose.crypto.data

import co.nimblehq.compose.crypto.core.JsonApiException
import co.nimblehq.compose.crypto.core.UnknownException
import co.nimblehq.compose.crypto.core.providers.MoshiBuilderProvider
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
private fun parseErrorResponse(source: String?): co.nimblehq.compose.crypto.core.model.response.ErrorResponse? {
    return try {
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val adapter = moshi.adapter(co.nimblehq.compose.crypto.core.model.response.ErrorResponse::class.java)
        adapter.fromJson(source.orEmpty())
    } catch (e: Exception) {
        null
    }
}
