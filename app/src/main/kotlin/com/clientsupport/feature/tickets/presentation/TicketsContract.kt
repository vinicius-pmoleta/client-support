package com.clientsupport.feature.tickets.presentation

import com.clientsupport.core.structure.BaseContract
import com.clientsupport.feature.tickets.presentation.model.TicketScreenModel

interface TicketsContract {

    interface View : BaseContract.View {

        fun displayProgress()

        fun hideProgress()
        fun showTickets(tickets: List<TicketScreenModel>)
        fun showError()
    }

    interface Action : BaseContract.Action {

        fun loadTicketsForView(viewId: Long)

        fun refreshTickets(viewId: Long)
    }
}