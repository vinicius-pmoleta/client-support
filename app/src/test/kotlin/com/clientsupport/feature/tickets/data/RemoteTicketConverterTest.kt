package com.clientsupport.feature.tickets.data

import com.clientsupport.core.data.repository.remote.TicketResponse
import com.clientsupport.feature.common.data.Status
import com.clientsupport.feature.common.data.Ticket
import org.junit.Assert.assertEquals
import org.junit.Test

class RemoteTicketConverterTest {

    private val converter = RemoteTicketConverter()

    @Test
    fun `when remote model value is received then verify business model`() {
        val response = TicketResponse(1, "subject", "description", "new",
                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z")

        val expected = Ticket(1, "subject", "description", Status.NEW,
                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z")

        assertEquals(expected, converter.fromRemote(response))
    }

}