package com.clientsupport.feature.tickets.di

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProviders
import com.clientsupport.core.data.repository.local.ClientSupportDatabase
import com.clientsupport.core.data.repository.remote.ClientSupportExternalApi
import com.clientsupport.core.di.FeatureScope
import com.clientsupport.feature.common.data.converter.TicketConverter
import com.clientsupport.feature.tickets.business.TicketsBusiness
import com.clientsupport.feature.tickets.data.LocalTicketsRepository
import com.clientsupport.feature.tickets.data.RemoteTicketsRepository
import com.clientsupport.feature.tickets.presentation.TicketsActivity
import com.clientsupport.feature.tickets.presentation.TicketsContract
import com.clientsupport.feature.tickets.presentation.TicketsDataHolder
import com.clientsupport.feature.tickets.presentation.TicketsPresenter
import dagger.Module
import dagger.Provides

@Module
class TicketsFeatureModule(private val activity: TicketsActivity) {

    @FeatureScope
    @Provides
    fun provideLifecycleOwner(): LifecycleOwner {
        return activity
    }

    @FeatureScope
    @Provides
    fun provideTicketsDataHolder(): TicketsDataHolder {
        return ViewModelProviders.of(activity).get(TicketsDataHolder::class.java)
    }

    @FeatureScope
    @Provides
    fun provideView(): TicketsContract.View {
        return activity
    }

    @FeatureScope
    @Provides
    fun providePresenter(view: TicketsContract.View,
                         lifecycleOwner: LifecycleOwner,
                         ticketsDataHolder: TicketsDataHolder,
                         business: TicketsBusiness): TicketsPresenter {
        return TicketsPresenter(view, lifecycleOwner, ticketsDataHolder, business)
    }

    @FeatureScope
    @Provides
    fun provideTicketBusiness(remoteRepository: RemoteTicketsRepository,
                              localRepository: LocalTicketsRepository): TicketsBusiness {
        return TicketsBusiness(remoteRepository, localRepository)
    }

    @FeatureScope
    @Provides
    fun provideRemoteRepository(externalApi: ClientSupportExternalApi,
                                converter: TicketConverter): RemoteTicketsRepository {
        return RemoteTicketsRepository(externalApi, converter)
    }

    @FeatureScope
    @Provides
    fun provideLocalRepository(database: ClientSupportDatabase,
                               converter: TicketConverter): LocalTicketsRepository {
        return LocalTicketsRepository(database.ticketDao(), converter)
    }

    @FeatureScope
    @Provides
    fun provideTicketConverter(): TicketConverter {
        return TicketConverter()
    }
}