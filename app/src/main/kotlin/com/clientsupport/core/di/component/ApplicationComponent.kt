package com.clientsupport.core.di.component

import com.clientsupport.core.ClientSupportApplication
import com.clientsupport.core.data.repository.local.ClientSupportDatabase
import com.clientsupport.core.data.repository.remote.ClientSupportExternalApi
import com.clientsupport.core.di.module.ApplicationModule
import com.clientsupport.core.di.module.LocalRepositoryModule
import com.clientsupport.core.di.module.NetworkModule
import com.clientsupport.core.di.module.RemoteRepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class, NetworkModule::class,
    LocalRepositoryModule::class, RemoteRepositoryModule::class
])
interface ApplicationComponent {

    fun application(): ClientSupportApplication

    fun externalApi(): ClientSupportExternalApi

    fun database(): ClientSupportDatabase

}