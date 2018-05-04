package com.clientsupport.feature.tickets.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Observer
import android.util.Log
import com.clientsupport.core.rx.NetworkTransformer
import com.clientsupport.feature.common.data.model.Ticket
import com.clientsupport.feature.common.data.model.TicketResult
import com.clientsupport.feature.tickets.business.TicketsBusiness

class TicketsPresenter(
        private val view: TicketsContract.View,
        private val owner: LifecycleOwner,
        private val ticketsDataHolder: TicketsDataHolder,
        private val business: TicketsBusiness) : TicketsContract.Action {

    override fun loadTicketsForView(viewId: Long) {
        ticketsDataHolder.result?.value?.let {
            handleTicketsResult(it)
        } ?: run {
            loadTickets(viewId)
        }
    }

    private fun loadTickets(viewId: Long) {
        val result = LiveDataReactiveStreams.fromPublisher(
                business.loadAllLocalTickets()
                        .compose(NetworkTransformer())
        )

        ticketsDataHolder.result = result
        result.observe(owner, Observer {
            handleTicketsResult(it)
        })

        refreshTickets(viewId)
    }

    private fun refreshTickets(viewId: Long) {
        business.updateTicketsForView(viewId)
                .compose(NetworkTransformer())
                .subscribe({ }, { error -> handleError(error) })
    }

    private fun handleTicketsResult(result: TicketResult?) {
        result?.data?.let {
            handleData(it)
        }
        result?.error?.let {
            handleError(it)
        }
    }

    private fun handleError(error: Throwable) {
        Log.d("RESULT", "ERROR", error)
    }

    private fun handleData(tickets: List<Ticket>) {
        Log.d("RESULT", tickets.toString())
    }
}