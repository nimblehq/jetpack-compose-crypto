package co.nimblehq.compose.crypto.data.detail.di.modules

import co.nimblehq.compose.crypto.data.detail.repository.CoinRepositoryImpl
import co.nimblehq.compose.crypto.data.service.ApiService
import co.nimblehq.compose.crypto.domain.detail.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryDetailModule {

    @Provides
    fun provideRepository(apiService: ApiService): CoinRepository =
        CoinRepositoryImpl(apiService)
}
