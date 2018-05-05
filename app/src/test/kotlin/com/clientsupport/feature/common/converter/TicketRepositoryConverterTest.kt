package com.clientsupport.feature.common.converter

import com.clientsupport.core.data.repository.local.entity.TicketEntity
import com.clientsupport.core.data.repository.remote.TicketResponse
import com.clientsupport.feature.common.data.converter.TicketRepositoryConverter
import com.clientsupport.feature.common.data.model.Status
import com.clientsupport.feature.common.data.model.Ticket
import org.junit.Assert.assertEquals
import org.junit.Test

class TicketRepositoryConverterTest {

    private val converter = TicketRepositoryConverter()

    @Test
    fun `when response goes to business layer then verify conversion to model`() {
        val response = TicketResponse(1, "subject", "description", "new",
                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z")

        val expected = Ticket(1, "subject", "description", Status.NEW,
                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z")

        assertEquals(expected, converter.fromRemote(response))
    }

    @Test
    fun `when entity goes to business then verify conversion to model`() {
        val entity = TicketEntity(1, "subject", "description", "new",
                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z")

        val expected = Ticket(1, "subject", "description", Status.NEW,
                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z")

        assertEquals(expected, converter.fromEntity(entity))
    }

    @Test
    fun `when model goes to database then verify conversion to entity`() {
        val model = Ticket(1, "subject", "description", Status.NEW,
                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z")

        val expected = TicketEntity(1, "subject", "description", "new",
                "2014-01-30T14:18:40Z", "2014-01-30T15:18:40Z")

        assertEquals(expected, converter.toEntity(model))
    }
}