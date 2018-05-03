package com.clientsupport.feature.tickets.data

import com.clientsupport.core.data.repository.remote.TicketResponse
import com.clientsupport.feature.common.data.Status
import com.clientsupport.feature.common.data.Ticket

class RemoteTicketConverter {

    fun fromRemote(response: TicketResponse): Ticket {
        return Ticket(
                id = response.id,
                subject = response.subject,
                description = response.description,
                status = Status.find(response.status),
                createdAt = response.createdAt,
                updateAt = response.updateAt
        )
    }
}