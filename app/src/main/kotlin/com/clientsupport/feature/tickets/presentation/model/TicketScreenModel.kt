package com.clientsupport.feature.tickets.presentation.model

data class TicketScreenModel(
        val id: String,
        val subject: String,
        val description: String,
        val lastUpdate: String,
        val status: String
)