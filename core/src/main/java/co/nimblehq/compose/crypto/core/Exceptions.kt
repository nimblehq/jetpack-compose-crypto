package co.nimblehq.compose.crypto.core

import co.nimblehq.compose.crypto.core.model.response.ErrorResponse
import retrofit2.HttpException
import retrofit2.Response

object UnknownException : RuntimeException()

object NoConnectivityException : RuntimeException()

data class JsonApiException(val error: ErrorResponse, val response: Response<*>) :
    HttpException(response)
