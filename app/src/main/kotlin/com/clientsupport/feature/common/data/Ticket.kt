package com.clientsupport.feature.common.data

data class Ticket(
        val id: Long,
        val subject: String,
        val description: String,
        val status: Status,
        var createdAt: String,
        var updateAt: String
)

enum class Status {

    NEW, OPEN, PENDING, HOLD, SOLVED, CLOSED, UNKNOWN;

    companion object {
        fun find(value: String): Status {
            return try {
                Status.valueOf(value.toUpperCase())
            } catch (e: IllegalArgumentException) {
                Status.UNKNOWN
            }
        }
    }
}