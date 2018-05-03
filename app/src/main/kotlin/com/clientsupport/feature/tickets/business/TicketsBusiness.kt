package com.clientsupport.feature.tickets.business

import android.support.annotation.WorkerThread
import com.clientsupport.feature.common.data.Ticket
import com.clientsupport.feature.tickets.data.RemoteTicketsRepository
import io.reactivex.Single

@WorkerThread
class TicketsBusiness(
        private val remoteRepository: RemoteTicketsRepository
) {

    fun fetchTicketsForView(viewId: Long): Single<List<Ticket>> {
        return remoteRepository.getTicketsForView(viewId)
    }
}