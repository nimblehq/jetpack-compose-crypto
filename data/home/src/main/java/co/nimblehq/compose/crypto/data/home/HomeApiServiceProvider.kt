package co.nimblehq.compose.crypto.data.home

import co.nimblehq.compose.crypto.data.home.service.HomeApiService
import retrofit2.Retrofit

object HomeApiServiceProvider {

    fun getHomeApiService(retrofit: Retrofit): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }
}
