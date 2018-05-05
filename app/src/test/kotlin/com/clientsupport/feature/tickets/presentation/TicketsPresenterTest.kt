package com.clientsupport.feature.tickets.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import com.clientsupport.core.rx.ExecutionConfiguration
import com.clientsupport.feature.common.data.model.Ticket
import com.clientsupport.feature.common.data.model.TicketResult
import com.clientsupport.feature.tickets.business.TicketsBusiness
import com.clientsupport.feature.tickets.presentation.model.TicketScreenConverter
import com.clientsupport.feature.tickets.presentation.model.TicketScreenModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.io.IOException

class TicketsPresenterTest {

    private val view = mockk<TicketsContract.View>(relaxed = true)

    private val owner = mockk<LifecycleOwner>(relaxed = true)

    private val dataHolder = mockk<TicketsDataHolder>(relaxed = true)

    private val converter = mockk<TicketScreenConverter>(relaxed = true)

    private val business = mockk<TicketsBusiness>(relaxed = true)

    private val configuration = ExecutionConfiguration(TestScheduler(), TestScheduler())

    private val presenter = TicketsPresenter(view, owner, dataHolder, converter, configuration, business)

    @Test
    fun `when tickets data is received then ask view to show it`() {
        val ticket1 = mockk<Ticket>(relaxed = true)
        val ticket2 = mockk<Ticket>(relaxed = true)

        val screen1 = mockk<TicketScreenModel>(relaxed = true)
        val screen2 = mockk<TicketScreenModel>(relaxed = true)

        every { converter.toScreenModel(ticket1) } returns screen1
        every { converter.toScreenModel(ticket2) } returns screen2

        presenter.handleData(listOf(ticket1, ticket2))

        verify(exactly = 1) { view.showTickets(listOf(screen1, screen2)) }
    }

    @Test
    fun `when tickets update result has error then ask view to show it and dismiss progress`() {
        val result = TicketResult(error = IOException())

        presenter.handleUpdateTicketsResult(result)

        verify(exactly = 1) { view.showError() }
        verify(exactly = 1) { view.hideProgress() }
    }

    @Test
    fun `when tickets update result has no error then ask view to dismiss progress`() {
        val result = TicketResult()

        presenter.handleUpdateTicketsResult(result)

        verify(exactly = 0) { view.showError() }
        verify(exactly = 1) { view.hideProgress() }
    }

    @Test
    fun `when tickets load result has error then ask view to show it`() {
        val result = TicketResult(error = IOException())

        presenter.handleLoadTicketsResult(result)

        verify(exactly = 1) { view.showError() }
        verify(exactly = 0) { view.showTickets(any()) }
        verify(exactly = 1) { view.hideProgress() }
    }

    @Test
    fun `when tickets load result has data then ask view to show it`() {
        val result = TicketResult(data = mockk(relaxed = true))

        presenter.handleLoadTicketsResult(result)

        verify(exactly = 0) { view.showError() }
        verify(exactly = 1) { view.showTickets(any()) }
        verify(exactly = 1) { view.hideProgress() }
    }

    @Test
    fun `when tickets load result has data and error then ask view to show error and data`() {
        val result = TicketResult(data = mockk(relaxed = true), error = IOException())

        presenter.handleLoadTicketsResult(result)

        verify(exactly = 1) { view.showError() }
        verify(exactly = 1) { view.showTickets(any()) }
        verify(exactly = 1) { view.hideProgress() }
    }

    @Test
    fun `when request to load tickets with held data then show it`() {
        val ticket = mockk<Ticket>(relaxed = true)
        val screen = mockk<TicketScreenModel>(relaxed = true)
        every { converter.toScreenModel(ticket) } returns screen

        val live = mockk<LiveData<TicketResult>>(relaxed = true)
        every { live.value } returns TicketResult(data = listOf(ticket))
        every { dataHolder.result } returns live

        presenter.loadTicketsForView(0)

        verify(exactly = 1) { view.displayProgress() }
        verify(exactly = 1) { view.showTickets(listOf(screen)) }
        verify(exactly = 0) { view.showError() }
        verify(exactly = 1) { view.hideProgress() }
    }

    @Test
    fun `when request to load tickets without held data then load local and trigger remote update`() {
        val result = TicketResult(data = listOf())

        every { business.loadAllLocalTickets() } returns Flowable.just(result)
        every { dataHolder.result } returns null

        presenter.loadTicketsForView(0)

        verify(exactly = 1) { view.displayProgress() }
        verify(exactly = 1) { business.loadAllLocalTickets() }
        verify(exactly = 1) { business.updateTicketsForView(0) }
    }
}