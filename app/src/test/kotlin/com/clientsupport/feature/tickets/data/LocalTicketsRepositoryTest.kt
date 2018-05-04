package com.clientsupport.feature.tickets.data

import com.clientsupport.core.data.repository.local.entity.TicketEntity
import com.clientsupport.feature.common.data.converter.TicketConverter
import com.clientsupport.feature.common.data.model.Status
import com.clientsupport.feature.common.data.model.Ticket
import com.clientsupport.feature.common.data.repository.TicketDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Flowable
import org.junit.Test
import java.io.IOException

class LocalTicketsRepositoryTest {

    private val ticketDao = mockk<TicketDao>(relaxed = true)

    private val localRepository = LocalTicketsRepository(ticketDao, TicketConverter())

    @Test
    fun `when ticket entities available on database then return the equivalent models`() {
        val entities = listOf(
                TicketEntity(1, "subject1", "description2", "new",
                        "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z"),
                TicketEntity(2, "subject2", "description2", "solved",
                        "2014-01-29T14:18:40Z", "2014-01-29T15:18:40Z")
        )

        every {
            ticketDao.queryAllTickets()
        } returns Flowable.just(entities)

        localRepository.loadTickets()
                .test()
                .assertNoErrors()
                .assertValue(listOf(
                        Ticket(1, "subject1", "description2", Status.NEW,
                                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z"),
                        Ticket(2, "subject2", "description2", Status.SOLVED,
                                "2014-01-29T14:18:40Z", "2014-01-29T15:18:40Z")
                ))
    }

    @Test
    fun `when ticket table is empty on database then no model is returned`() {
        every {
            ticketDao.queryAllTickets()
        } returns Flowable.empty()

        localRepository.loadTickets()
                .test()
                .assertComplete()
                .assertNoValues()
    }

    @Test
    fun `when querying the database failed then no model is returned`() {
        val error = IOException()
        every {
            ticketDao.queryAllTickets()
        } returns Flowable.error(error)

        localRepository.loadTickets()
                .test()
                .assertError(error)
                .assertNoValues()
    }

    @Test
    fun `when ticket models available then store them on database`() {
        val tickets = listOf(
                Ticket(1, "subject1", "description2", Status.NEW,
                        "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z"),
                Ticket(2, "subject2", "description2", Status.SOLVED,
                        "2014-01-29T14:18:40Z", "2014-01-29T15:18:40Z")
        )

        localRepository.storeTickets(tickets)

        verify(exactly = 1) {
            ticketDao.insertTickets(listOf(
                    TicketEntity(1, "subject1", "description2", "new",
                            "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z"),
                    TicketEntity(2, "subject2", "description2", "solved",
                            "2014-01-29T14:18:40Z", "2014-01-29T15:18:40Z")
            ))
        }
    }
}