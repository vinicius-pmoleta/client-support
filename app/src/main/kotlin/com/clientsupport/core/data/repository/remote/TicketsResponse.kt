package com.clientsupport.core.data.repository.remote

import com.squareup.moshi.Json

data class TicketsResponse(
        var tickets: List<TicketResponse>
)

data class TicketResponse(
        var id: Long,
        var subject: String,
        var description: String,
        var status: String,
        @Json(name = "created_at") var createdAt: String,
        @Json(name = "updated_at") var updateAt: String
)