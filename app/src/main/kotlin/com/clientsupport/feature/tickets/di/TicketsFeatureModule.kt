package com.clientsupport.feature.tickets.di

import com.clientsupport.core.data.repository.remote.RemoteClientSupportRepository
import com.clientsupport.core.di.FeatureScope
import com.clientsupport.feature.tickets.business.TicketsBusiness
import com.clientsupport.feature.tickets.data.RemoteTicketConverter
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
    fun provideTicketBusiness(remoteRepository: RemoteTicketsRepository): TicketsBusiness {
        return TicketsBusiness(remoteRepository)
    }

    @FeatureScope
    @Provides
    fun provideRemoteRepository(externalRemoteRepository: RemoteClientSupportRepository,
                                converter: RemoteTicketConverter): RemoteTicketsRepository {
        return RemoteTicketsRepository(externalRemoteRepository, converter)
    }

    @FeatureScope
    @Provides
    fun provideRemoteTicketConverter(): RemoteTicketConverter {
        return RemoteTicketConverter()
    }
}