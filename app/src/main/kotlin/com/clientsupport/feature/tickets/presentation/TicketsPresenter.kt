package com.clientsupport.feature.tickets.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Observer
import com.clientsupport.core.rx.ExecutionConfiguration
import com.clientsupport.core.rx.ExecutionTransformer
import com.clientsupport.feature.common.data.model.Ticket
import com.clientsupport.feature.common.data.model.TicketResult
import com.clientsupport.feature.tickets.business.TicketsBusiness
import com.clientsupport.feature.tickets.presentation.model.TicketScreenConverter

class TicketsPresenter(
        private val view: TicketsContract.View,
        private val owner: LifecycleOwner,
        private val ticketsDataHolder: TicketsDataHolder,
        private val converter: TicketScreenConverter,
        private val configuration: ExecutionConfiguration,
        private val business: TicketsBusiness) : TicketsContract.Action {

    override fun loadTicketsForView(viewId: Long) {
        view.displayProgress()
        ticketsDataHolder.result?.value?.let {
            handleLoadTicketsResult(it)
        } ?: run {
            loadLocalTickets()
            refreshTickets(viewId)
        }
    }

    private fun loadLocalTickets() {
        val result = LiveDataReactiveStreams.fromPublisher(
                business.loadAllLocalTickets()
                        .compose(ExecutionTransformer(configuration))
        )

        ticketsDataHolder.result = result
        result.observe(owner, Observer {
            handleLoadTicketsResult(it)
        })
    }

    override fun refreshTickets(viewId: Long) {
        business.updateTicketsForView(viewId)
                .compose(ExecutionTransformer(configuration))
                .subscribe({ result -> handleUpdateTicketsResult(result) }, { _ -> handleError() })
    }

    internal fun handleLoadTicketsResult(result: TicketResult?) {
        result?.data?.let {
            handleData(it)
        }
        result?.error?.let {
            handleError()
        }
        view.hideProgress()
    }

    internal fun handleUpdateTicketsResult(result: TicketResult?) {
        result?.error?.let {
            handleError()
        }
        view.hideProgress()
    }

    private fun handleError() {
        view.showError()
    }

    internal fun handleData(tickets: List<Ticket>) {
        if (tickets.isNotEmpty()) {
            view.hideEmptyState()
        } else {
            view.showEmptyState()
        }
        view.showTickets(tickets.map { ticket -> converter.toScreenModel(ticket) })
    }
}