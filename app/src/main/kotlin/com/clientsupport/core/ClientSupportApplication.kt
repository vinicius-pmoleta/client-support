package com.clientsupport.core

import android.app.Application
import com.clientsupport.core.di.component.ApplicationComponent
import com.clientsupport.core.di.component.DaggerApplicationComponent
import com.clientsupport.core.di.module.ApplicationModule
import com.clientsupport.core.di.module.LocalRepositoryModule
import com.clientsupport.core.di.module.NetworkModule
import com.clientsupport.core.di.module.RemoteRepositoryModule

class ClientSupportApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initializeDependencyInjection()
    }

    private fun initializeDependencyInjection() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule())
                .localRepositoryModule(LocalRepositoryModule())
                .remoteRepositoryModule(RemoteRepositoryModule())
                .build()
    }

}