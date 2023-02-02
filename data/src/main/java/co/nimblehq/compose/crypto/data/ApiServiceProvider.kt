package co.nimblehq.compose.crypto.data

import co.nimblehq.compose.crypto.data.home.service.HomeApiService
import co.nimblehq.compose.crypto.data.service.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    fun getHomeApiService(retrofit: Retrofit): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }
}
