package co.nimblehq.compose.crypto.data.detail

import co.nimblehq.compose.crypto.data.service.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
