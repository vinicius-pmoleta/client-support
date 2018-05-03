package com.clientsupport.feature.tickets.business

import com.clientsupport.feature.tickets.data.RemoteTicketsRepository
import io.mockk.mockk

class TicketsBusinessTest {

    private val remoteRepository = mockk<RemoteTicketsRepository>(relaxed = true)

    private val business = TicketsBusiness(remoteRepository)



}