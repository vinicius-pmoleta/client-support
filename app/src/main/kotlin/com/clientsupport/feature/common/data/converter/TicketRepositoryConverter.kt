package com.clientsupport.feature.common.data.converter

import com.clientsupport.core.data.repository.local.entity.TicketEntity
import com.clientsupport.core.data.repository.remote.TicketResponse
import com.clientsupport.feature.common.data.model.Status
import com.clientsupport.feature.common.data.model.Ticket

class TicketRepositoryConverter {

    fun fromRemote(response: TicketResponse): Ticket {
        return Ticket(
                id = response.id,
                subject = response.subject,
                description = response.description,
                status = Status.find(response.status),
                createdAt = response.createdAt,
                updatedAt = response.updateAt
        )
    }

    fun fromEntity(entity: TicketEntity): Ticket {
        return Ticket(
                id = entity.id,
                subject = entity.subject,
                description = entity.description,
                status = Status.find(entity.status),
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
        )
    }

    fun toEntity(ticket: Ticket): TicketEntity {
        return TicketEntity(
                id = ticket.id,
                subject = ticket.subject,
                description = ticket.description,
                status = ticket.status.name.toLowerCase(),
                createdAt = ticket.createdAt,
                updatedAt = ticket.updatedAt
        )
    }
}