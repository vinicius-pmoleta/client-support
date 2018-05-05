package com.clientsupport.feature.tickets.data

import android.support.annotation.WorkerThread
import com.clientsupport.feature.common.data.converter.TicketRepositoryConverter
import com.clientsupport.feature.common.data.model.Ticket
import com.clientsupport.feature.common.data.repository.TicketDao
import io.reactivex.Flowable

@WorkerThread
class LocalTicketsRepository(
        private val ticketDao: TicketDao,
        private val converter: TicketRepositoryConverter) {

    fun loadTickets(): Flowable<List<Ticket>> {
        return ticketDao.queryAllTickets()
                .flatMap { entities ->
                    Flowable.fromIterable(entities)
                            .map { entity -> converter.fromEntity(entity) }
                            .toList()
                            .toFlowable()
                }
    }

    fun storeTickets(tickets: List<Ticket>) {
        ticketDao.insertTickets(
                tickets.map { ticket -> converter.toEntity(ticket) }
        )
    }
}