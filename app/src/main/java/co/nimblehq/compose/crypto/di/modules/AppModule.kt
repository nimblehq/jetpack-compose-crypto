package co.nimblehq.compose.crypto.di.modules

import android.content.Context
import co.nimblehq.compose.crypto.data.repository.GlobalRepositoryImpl
import co.nimblehq.compose.crypto.domain.repository.GlobalRepository
import co.nimblehq.compose.crypto.util.DispatchersProvider
import co.nimblehq.compose.crypto.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl()
    }

    @Provides
    fun provideGlobalRepository(
        @ApplicationContext
        appContext: Context
    ): GlobalRepository {
        return GlobalRepositoryImpl(appContext)
    }
}
