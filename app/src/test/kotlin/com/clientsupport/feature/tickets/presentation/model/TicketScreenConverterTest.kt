package com.clientsupport.feature.tickets.presentation.model

import android.content.Context
import com.clientsupport.R
import com.clientsupport.feature.common.data.model.Status
import com.clientsupport.feature.common.data.model.Ticket
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TicketScreenConverterTest {

    private lateinit var converter: TicketScreenConverter

    @Before
    fun setup() {
        val context = mockk<Context>(relaxed = true)
        every { context.getString(R.string.tickets_id) } returns "#%d"
        every { context.getString(R.string.tickets_last_update) } returns "Last update: %s"

        converter = TicketScreenConverter(context)
    }

    @Test
    fun `verify last update date formatted from repository to screen`() {
        val result = converter.formatLastUpdate("2014-01-30T14:17:26Z")
        assertEquals("30/01/14 14:17", result)
    }

    @Test
    fun `verify screen model created from business model`() {
        val ticket = Ticket(1, "Subject", "Description", Status.OPEN,
                "2014-01-30T14:17:26Z", "2014-01-30T14:17:26Z")

        val result = converter.toScreenModel(ticket)

        val expected = TicketScreenModel("#1", "Subject", "Description",
                "Last update: 30/01/14 14:17", "OPEN")

        assertEquals(expected, result)
    }
}