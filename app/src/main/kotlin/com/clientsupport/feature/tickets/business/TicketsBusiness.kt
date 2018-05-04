package com.clientsupport.feature.tickets.business

import android.support.annotation.WorkerThread
import com.clientsupport.feature.common.data.model.TicketResult
import com.clientsupport.feature.tickets.data.LocalTicketsRepository
import com.clientsupport.feature.tickets.data.RemoteTicketsRepository
import io.reactivex.Flowable

@WorkerThread
class TicketsBusiness(
        private val remoteRepository: RemoteTicketsRepository,
        private val localRepository: LocalTicketsRepository) {

    fun loadAllLocalTickets(): Flowable<TicketResult> {
        return localRepository.loadTickets()
                .map { data -> TicketResult(data = data) }
                .onErrorReturn { error -> TicketResult(error = error) }
    }

    fun updateTicketsForView(viewId: Long): Flowable<TicketResult> {
        return remoteRepository.fetchTicketsForView(viewId)
                .doOnNext { tickets ->
                    localRepository.storeTickets(tickets)
                }
                .map { data -> TicketResult(data = data) }
                .onErrorReturn { error -> TicketResult(error = error) }
    }
}