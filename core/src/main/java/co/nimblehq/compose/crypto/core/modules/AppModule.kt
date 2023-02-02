package co.nimblehq.compose.crypto.core.modules

import co.nimblehq.compose.crypto.core.util.DispatchersProvider
import co.nimblehq.compose.crypto.core.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl()
    }
}
