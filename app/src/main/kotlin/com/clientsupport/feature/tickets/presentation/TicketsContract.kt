package com.clientsupport.feature.tickets.presentation

import com.clientsupport.core.structure.BaseContract

interface TicketsContract {

    interface View : BaseContract.View

    interface Action : BaseContract.Action {
        fun loadTicketsForView(viewId: Long)
    }
}