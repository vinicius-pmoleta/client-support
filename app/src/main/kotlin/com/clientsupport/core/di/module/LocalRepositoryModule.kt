package com.clientsupport.core.di.module

import android.arch.persistence.room.Room
import com.clientsupport.core.ClientSupportApplication
import com.clientsupport.core.data.repository.local.ClientSupportDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalRepositoryModule {

    @Provides
    @Singleton
    fun provideDatabase(application: ClientSupportApplication): ClientSupportDatabase {
        return Room.databaseBuilder(
                application.applicationContext,
                ClientSupportDatabase::class.java,
                "client-support-database"
        ).build()
    }
}