package com.clientsupport.core.di.module

import com.clientsupport.core.ClientSupportApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: ClientSupportApplication) {

    @Provides
    @Singleton
    fun provideApplication(): ClientSupportApplication {
        return application
    }
}