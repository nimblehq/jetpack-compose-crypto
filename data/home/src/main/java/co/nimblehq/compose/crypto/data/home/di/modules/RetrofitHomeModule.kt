package co.nimblehq.compose.crypto.data.home.di.modules

import co.nimblehq.compose.crypto.data.home.HomeApiServiceProvider
import co.nimblehq.compose.crypto.data.home.service.HomeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitHomeModule {

    @Provides
    fun provideHomeApiService(retrofit: Retrofit): HomeApiService =
        HomeApiServiceProvider.getHomeApiService(retrofit)
}
