package com.clientsupport.feature.tickets.data

import android.support.annotation.WorkerThread
import com.clientsupport.core.data.repository.remote.RemoteClientSupportRepository
import com.clientsupport.feature.common.data.Ticket
import io.reactivex.Observable
import io.reactivex.Single

@WorkerThread
class RemoteTicketsRepository(
        private val repository: RemoteClientSupportRepository,
        private val converter: RemoteTicketConverter
) {

    fun getTicketsForView(viewId: Long): Single<List<Ticket>> {
        return repository.getTicketsForView(viewId)
                .flatMap { responses -> Observable.fromIterable(responses.tickets) }
                .map { response -> converter.fromRemote(response) }
                .toList()
    }
}