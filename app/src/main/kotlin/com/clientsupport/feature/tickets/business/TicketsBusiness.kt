package com.clientsupport.feature.tickets.business

import android.support.annotation.WorkerThread
import com.clientsupport.feature.common.data.model.Ticket
import com.clientsupport.feature.tickets.data.LocalTicketsRepository
import com.clientsupport.feature.tickets.data.RemoteTicketsRepository
import io.reactivex.Flowable

@WorkerThread
class TicketsBusiness(
        private val remoteRepository: RemoteTicketsRepository,
        private val localRepository: LocalTicketsRepository) {

    fun loadTicketsForView(): Flowable<List<Ticket>> {
        return localRepository.loadTickets()
    }

    fun fetchTicketsForView(viewId: Long): Flowable<List<Ticket>> {
        return remoteRepository.getTicketsForView(viewId)
                .doOnNext { tickets ->
                    localRepository.storeTickets(tickets)
                }
    }
}