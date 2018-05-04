package com.clientsupport.feature.tickets.data

import com.clientsupport.core.data.repository.remote.ClientSupportExternalApi
import com.clientsupport.core.data.repository.remote.TicketResponse
import com.clientsupport.core.data.repository.remote.TicketsResponse
import com.clientsupport.feature.common.data.converter.TicketConverter
import com.clientsupport.feature.common.data.model.Status
import com.clientsupport.feature.common.data.model.Ticket
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import org.junit.Test
import java.io.IOException

class RemoteTicketsRepositoryTest {

    private val externalApi = mockk<ClientSupportExternalApi>(relaxed = true)

    private val remoteRepository = RemoteTicketsRepository(externalApi, TicketConverter())

    @Test
    fun `when received external response then verify conversion to business model`() {
        val ticketsResponse = listOf(
                TicketResponse(1, "subject1", "description1", "new",
                        "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z"),
                TicketResponse(2, "subject2", "description2", "solved",
                        "2014-01-29T14:18:40Z", "2014-01-29T15:18:40Z")
        )

        every {
            externalApi.getTicketsForView(0)
        } returns Flowable.just(TicketsResponse(ticketsResponse))

        remoteRepository.fetchTicketsForView(0)
                .test()
                .assertNoErrors()
                .assertValue(listOf(
                        Ticket(1, "subject1", "description1", Status.NEW,
                                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z"),
                        Ticket(2, "subject2", "description2", Status.SOLVED,
                                "2014-01-29T14:18:40Z", "2014-01-29T15:18:40Z")
                ))
    }

    @Test
    fun `when received external error then verify conversion failed`() {
        val error = IOException()
        every {
            externalApi.getTicketsForView(0)
        } returns Flowable.error(error)

        remoteRepository.fetchTicketsForView(0)
                .test()
                .assertError(error)
    }
}