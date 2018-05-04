package com.clientsupport.core.di.module

import com.clientsupport.core.data.repository.remote.ClientSupportExternalApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RemoteRepositoryModule {

    @Provides
    @Singleton
    fun provideExternalApi(retrofit: Retrofit): ClientSupportExternalApi {
        return retrofit.create(ClientSupportExternalApi::class.java)
    }
}