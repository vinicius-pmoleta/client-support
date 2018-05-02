package com.clientsupport.core.di.module

import com.clientsupport.core.data.repository.remote.RemoteClientSupportRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RemoteRepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(retrofit: Retrofit): RemoteClientSupportRepository {
        return retrofit.create(RemoteClientSupportRepository::class.java)
    }
}