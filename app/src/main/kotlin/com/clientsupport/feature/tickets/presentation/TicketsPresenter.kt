package com.clientsupport.feature.tickets.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Observer
import com.clientsupport.core.rx.IOTransformer
import com.clientsupport.feature.common.data.model.Ticket
import com.clientsupport.feature.common.data.model.TicketResult
import com.clientsupport.feature.tickets.business.TicketsBusiness
import com.clientsupport.feature.tickets.presentation.model.TicketScreenConverter

class TicketsPresenter(
        private val view: TicketsContract.View,
        private val owner: LifecycleOwner,
        private val ticketsDataHolder: TicketsDataHolder,
        private val converter: TicketScreenConverter,
        private val business: TicketsBusiness) : TicketsContract.Action {

    override fun loadTicketsForView(viewId: Long) {
        view.displayProgress()
        ticketsDataHolder.result?.value?.let {
            handleTicketsResult(it)
        } ?: run {
            loadLocalTickets()
            refreshTickets(viewId)
        }
    }

    private fun loadLocalTickets() {
        val result = LiveDataReactiveStreams.fromPublisher(
                business.loadAllLocalTickets()
                        .compose(IOTransformer())
        )

        ticketsDataHolder.result = result
        result.observe(owner, Observer {
            handleTicketsResult(it)
        })
    }

    override fun refreshTickets(viewId: Long) {
        business.updateTicketsForView(viewId)
                .compose(IOTransformer())
                .subscribe({ result -> handleTicketsUpdate(result) }, { _ -> handleError() })
    }

    private fun handleTicketsResult(result: TicketResult?) {
        result?.data?.let {
            handleData(it)
        }
        result?.error?.let {
            handleError()
        }
        view.hideProgress()
    }

    private fun handleTicketsUpdate(result: TicketResult?) {
        result?.error?.let {
            handleError()
        }
        view.hideProgress()
    }

    private fun handleError() {
        view.showError()
    }

    private fun handleData(tickets: List<Ticket>) {
        view.showTickets(tickets.map { ticket -> converter.toScreenModel(ticket) })
    }
}