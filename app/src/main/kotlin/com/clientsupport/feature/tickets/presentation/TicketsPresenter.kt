package com.clientsupport.feature.tickets.presentation

import android.util.Log
import com.clientsupport.feature.tickets.business.TicketsBusiness
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TicketsPresenter(
        private val view: TicketsContract.View,
        private val business: TicketsBusiness) : TicketsContract.Action {

    override fun loadTicketsForView(viewId: Long) {
        business.fetchTicketsForView(viewId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.d("REMOTE", result.toString()) },
                        { error -> Log.e("REMOTE", "Error", error) }
                )

        business.loadTicketsForView()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.d("LOCAL", result.toString()) },
                        { error -> Log.e("LOCAL", "Error", error) }
                )
    }
}