package co.nimblehq.compose.crypto.data.service

import co.nimblehq.compose.crypto.data.model.response.ErrorResponse
import retrofit2.HttpException
import retrofit2.Response

object UnknownException : RuntimeException()

object NoConnectivityException : RuntimeException()

data class JsonApiException(val error: ErrorResponse, val response: Response<*>) :
    HttpException(response)
