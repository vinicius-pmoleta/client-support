package com.clientsupport.feature.common.data

import com.clientsupport.feature.common.data.model.Status
import org.junit.Assert.assertEquals
import org.junit.Test

class TicketModelTest {

    @Test
    fun `when status value in lower case is found then return equivalent status`() {
        val result = Status.find("open")
        assertEquals(Status.OPEN, result)
    }

    @Test
    fun `when status value in upper case is found then return equivalent status`() {
        val result = Status.find("OPEN")
        assertEquals(Status.OPEN, result)
    }

    @Test
    fun `when status value is not found then return unknown status`() {
        val result = Status.find("test")
        assertEquals(Status.UNKNOWN, result)
    }
}