package co.nimblehq.compose.crypto.di.modules

import co.nimblehq.compose.crypto.ui.screens.MainNavigator
import co.nimblehq.compose.crypto.ui.screens.MainNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun mainNavigator(mainNavigator: MainNavigatorImpl): MainNavigator
}
