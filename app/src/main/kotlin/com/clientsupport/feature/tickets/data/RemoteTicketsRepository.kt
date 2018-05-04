package com.clientsupport.feature.tickets.data

import android.support.annotation.WorkerThread
import com.clientsupport.core.data.repository.remote.ClientSupportExternalApi
import com.clientsupport.feature.common.data.converter.TicketConverter
import com.clientsupport.feature.common.data.model.Ticket
import io.reactivex.Flowable

@WorkerThread
class RemoteTicketsRepository(
        private val externalApi: ClientSupportExternalApi,
        private val converter: TicketConverter) {

    fun fetchTicketsForView(viewId: Long): Flowable<List<Ticket>> {
        return externalApi.getTicketsForView(viewId)
                .flatMap { ticketsResponse ->
                    Flowable.fromIterable(ticketsResponse.tickets)
                            .map { ticketResponse -> converter.fromRemote(ticketResponse) }
                            .toList()
                            .toFlowable()
                }
    }
}