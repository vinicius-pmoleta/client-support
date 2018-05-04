package com.clientsupport.feature.tickets.business

import com.clientsupport.feature.common.data.model.Status
import com.clientsupport.feature.common.data.model.Ticket
import com.clientsupport.feature.common.data.model.TicketResult
import com.clientsupport.feature.tickets.data.LocalTicketsRepository
import com.clientsupport.feature.tickets.data.RemoteTicketsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Flowable
import org.junit.Test
import java.io.IOException

class TicketsBusinessTest {

    private val remoteRepository = mockk<RemoteTicketsRepository>(relaxed = true)

    private val localRepository = mockk<LocalTicketsRepository>(relaxed = true)

    private val business = TicketsBusiness(remoteRepository, localRepository)

    @Test
    fun `when fetched remote tickets then store them in the database`() {
        val tickets = listOf(
                Ticket(1, "subject1", "description1", Status.NEW,
                        "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z"),
                Ticket(2, "subject2", "description2", Status.SOLVED,
                        "2014-01-29T14:18:40Z", "2014-01-29T15:18:40Z")
        )

        every {
            remoteRepository.fetchTicketsForView(0)
        } returns Flowable.just(tickets)

        business.updateTicketsForView(0)
                .doOnComplete {
                    verify(exactly = 1) { localRepository.storeTickets(tickets) }
                }
                .test()
                .assertNoErrors()
                .assertValue(TicketResult(data = tickets))
    }

    @Test
    fun `when could not fetch remote tickets then don't interact with the database`() {
        val error = IOException()
        every {
            remoteRepository.fetchTicketsForView(0)
        } returns Flowable.error(error)

        business.updateTicketsForView(0)
                .doOnError {
                    verify(exactly = 0) { localRepository.storeTickets(any()) }
                }
                .test()
                .assertNoErrors()
                .assertValue(TicketResult(error = error))
    }

    @Test
    fun `when loaded local tickets then return result with data`() {
        val tickets = listOf(
                Ticket(1, "subject1", "description1", Status.NEW,
                        "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z"),
                Ticket(2, "subject2", "description2", Status.SOLVED,
                        "2014-01-29T14:18:40Z", "2014-01-29T15:18:40Z")
        )

        every {
            localRepository.loadTickets()
        } returns Flowable.just(tickets)

        business.loadAllLocalTickets()
                .test()
                .assertNoErrors()
                .assertValue(TicketResult(data = tickets))
    }

    @Test
    fun `when could not load local tickets then return result with error`() {
        val error = IOException()
        every {
            localRepository.loadTickets()
        } returns Flowable.error(error)

        business.loadAllLocalTickets()
                .test()
                .assertNoErrors()
                .assertValue(TicketResult(error = error))
    }
}