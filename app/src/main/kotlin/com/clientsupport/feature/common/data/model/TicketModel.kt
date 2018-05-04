package com.clientsupport.feature.common.data.model

data class Ticket(
        val id: Long,
        val subject: String,
        val description: String,
        val status: Status,
        var createdAt: String,
        var updatedAt: String
)

data class TicketResult(
        val data: List<Ticket>? = null,
        val error: Throwable? = null
)

enum class Status {

    NEW, OPEN, PENDING, HOLD, SOLVED, CLOSED, UNKNOWN;

    companion object {
        fun find(value: String): Status {
            return try {
                Status.valueOf(value.toUpperCase())
            } catch (e: IllegalArgumentException) {
                UNKNOWN
            }
        }
    }
}