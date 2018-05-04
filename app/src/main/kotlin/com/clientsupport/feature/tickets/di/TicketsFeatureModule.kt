package com.clientsupport.feature.tickets.di

import com.clientsupport.core.data.repository.local.ClientSupportDatabase
import com.clientsupport.core.data.repository.remote.ClientSupportExternalApi
import com.clientsupport.core.di.FeatureScope
import com.clientsupport.feature.common.data.converter.TicketConverter
import com.clientsupport.feature.tickets.business.TicketsBusiness
import com.clientsupport.feature.tickets.data.LocalTicketsRepository
import com.clientsupport.feature.tickets.data.RemoteTicketsRepository
import com.clientsupport.feature.tickets.presentation.TicketsContract
import com.clientsupport.feature.tickets.presentation.TicketsPresenter
import dagger.Module
import dagger.Provides

@Module
class TicketsFeatureModule(private val view: TicketsContract.View) {

    @FeatureScope
    @Provides
    fun providePresenter(business: TicketsBusiness): TicketsPresenter {
        return TicketsPresenter(view, business)
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