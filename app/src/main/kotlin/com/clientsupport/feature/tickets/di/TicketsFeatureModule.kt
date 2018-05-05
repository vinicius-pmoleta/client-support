package com.clientsupport.feature.tickets.di

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProviders
import com.clientsupport.core.data.repository.local.ClientSupportDatabase
import com.clientsupport.core.data.repository.remote.ClientSupportExternalApi
import com.clientsupport.core.di.FeatureScope
import com.clientsupport.core.rx.ExecutionConfiguration
import com.clientsupport.feature.common.data.converter.TicketRepositoryConverter
import com.clientsupport.feature.tickets.business.TicketsBusiness
import com.clientsupport.feature.tickets.data.LocalTicketsRepository
import com.clientsupport.feature.tickets.data.RemoteTicketsRepository
import com.clientsupport.feature.tickets.presentation.TicketsActivity
import com.clientsupport.feature.tickets.presentation.TicketsContract
import com.clientsupport.feature.tickets.presentation.TicketsDataHolder
import com.clientsupport.feature.tickets.presentation.TicketsPresenter
import com.clientsupport.feature.tickets.presentation.model.TicketScreenConverter
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
    fun provideScreenTicketConverter(): TicketScreenConverter {
        return TicketScreenConverter(activity)
    }

    @FeatureScope
    @Provides
    fun providePresenter(view: TicketsContract.View,
                         lifecycleOwner: LifecycleOwner,
                         ticketsDataHolder: TicketsDataHolder,
                         converter: TicketScreenConverter,
                         business: TicketsBusiness): TicketsPresenter {
        val configuration = ExecutionConfiguration(Schedulers.io(), AndroidSchedulers.mainThread())
        return TicketsPresenter(view, lifecycleOwner, ticketsDataHolder, converter, configuration, business)
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
                                converter: TicketRepositoryConverter): RemoteTicketsRepository {
        return RemoteTicketsRepository(externalApi, converter)
    }

    @FeatureScope
    @Provides
    fun provideLocalRepository(database: ClientSupportDatabase,
                               converter: TicketRepositoryConverter): LocalTicketsRepository {
        return LocalTicketsRepository(database.ticketDao(), converter)
    }

    @FeatureScope
    @Provides
    fun provideTicketrepositoryConverter(): TicketRepositoryConverter {
        return TicketRepositoryConverter()
    }
}