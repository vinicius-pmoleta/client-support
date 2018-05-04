package com.clientsupport.feature.tickets.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.clientsupport.feature.common.data.model.TicketResult

class TicketsDataHolder(var result: LiveData<TicketResult>? = null) : ViewModel()