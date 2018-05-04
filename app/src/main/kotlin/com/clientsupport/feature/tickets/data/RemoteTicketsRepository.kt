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

    fun getTicketsForView(viewId: Long): Flowable<List<Ticket>> {
        return externalApi.getTicketsForView(viewId)
                .flatMap { response -> Flowable.fromIterable(response.tickets) }
                .map { response -> converter.fromRemote(response) }
                .toList()
                .toFlowable()
    }
}