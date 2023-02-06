package co.nimblehq.compose.crypto.data.home.di.modules

import co.nimblehq.compose.crypto.data.home.repository.HomeRepositoryImpl
import co.nimblehq.compose.crypto.data.home.service.HomeApiService
import co.nimblehq.compose.crypto.domain.home.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryHomeModule {

    @Provides
    fun provideHomeRepository(apiService: HomeApiService): HomeRepository =
        HomeRepositoryImpl(apiService)
}
