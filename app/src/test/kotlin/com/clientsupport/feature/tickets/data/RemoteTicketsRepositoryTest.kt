package com.clientsupport.feature.tickets.data

import com.clientsupport.core.data.repository.remote.RemoteClientSupportRepository
import com.clientsupport.core.data.repository.remote.TicketResponse
import com.clientsupport.core.data.repository.remote.TicketsResponse
import com.clientsupport.feature.common.data.Status
import com.clientsupport.feature.common.data.Ticket
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test
import java.io.IOException

class RemoteTicketsRepositoryTest {

    private val remoteExternalRepository = mockk<RemoteClientSupportRepository>(relaxed = true)

    private val remoteRepository = RemoteTicketsRepository(remoteExternalRepository, RemoteTicketConverter())

    @Test
    fun `when received external response then verify conversion to business model`() {
        val ticketsResponse = listOf(
                TicketResponse(1, "subject1", "description1", "new",
                        "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z"),
                TicketResponse(2, "subject2", "description2", "solved",
                        "2014-01-29T14:18:40Z", "2014-01-29T15:18:40Z")
        )

        every {
            remoteExternalRepository.getTicketsForView(0)
        } returns Observable.just(TicketsResponse(ticketsResponse))

        remoteRepository.getTicketsForView(0)
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
            remoteExternalRepository.getTicketsForView(0)
        } returns Observable.error(error)

        remoteRepository.getTicketsForView(0)
                .test()
                .assertError(error)
    }
}