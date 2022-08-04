package co.nimblehq.compose.crypto.data

import co.nimblehq.compose.crypto.data.model.response.ErrorResponse
import co.nimblehq.compose.crypto.data.service.JsonApiException
import co.nimblehq.compose.crypto.data.service.UnknownException
import co.nimblehq.compose.crypto.data.service.providers.MoshiBuilderProvider
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import retrofit2.Response

@OptIn(FlowPreview::class)
fun <T> Flow<Response<T>>.transform(): Flow<T> = transform { response ->
    flow {
        val body = response.body()
        if (response.isSuccessful && body != null) {
            emit(body)
        } else {
            error(mapError(response))
        }
    }
}

private fun <T> mapError(response: Response<T>?): Exception {
    val errorBody = response?.errorBody()?.string()
    val errorResponse = parseErrorResponse(errorBody)

    return if (errorResponse != null && response != null) JsonApiException(errorResponse, response)
    else UnknownException
}

private fun parseErrorResponse(source: String?): ErrorResponse? {
    return try {
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val adapter = moshi.adapter(ErrorResponse::class.java)
        adapter.fromJson(source.orEmpty())
    } catch (e: Exception) {
        null
    }
}
