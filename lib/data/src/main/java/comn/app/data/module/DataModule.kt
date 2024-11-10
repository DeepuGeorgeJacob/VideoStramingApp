package comn.app.data.module

import comn.app.data.provider.VideoStreamDataProvider
import comn.app.data.provider.VideoStreamDataProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideVideoStreamLink(videoStreamDataProviderImpl: VideoStreamDataProviderImpl): VideoStreamDataProvider


}