package co.nimblehq.compose.crypto.data.detail.di.modules

import co.nimblehq.compose.crypto.data.detail.ApiServiceProvider
import co.nimblehq.compose.crypto.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitDetailModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        ApiServiceProvider.getApiService(retrofit)
}
