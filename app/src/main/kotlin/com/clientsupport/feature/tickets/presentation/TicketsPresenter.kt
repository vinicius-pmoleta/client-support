package com.clientsupport.feature.tickets.presentation

import android.util.Log
import com.clientsupport.feature.tickets.business.TicketsBusiness
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TicketsPresenter(
        private val view: TicketsContract.View,
        private val business: TicketsBusiness
) : TicketsContract.Action {

    override fun loadTicketsForView(viewId: Long) {
        business.fetchTicketsForView(viewId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.d("Test", result.toString()) },
                        { error -> Log.e("Test", "Error", error) }
                )
    }
}