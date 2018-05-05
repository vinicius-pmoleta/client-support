package com.clientsupport.feature.tickets.presentation

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.clientsupport.BuildConfig
import com.clientsupport.R
import com.clientsupport.core.ClientSupportApplication
import com.clientsupport.core.structure.BaseActivity
import com.clientsupport.feature.tickets.di.DaggerTicketsFeatureComponent
import com.clientsupport.feature.tickets.di.TicketsFeatureModule
import com.clientsupport.feature.tickets.presentation.adapter.TicketsAdapter
import com.clientsupport.feature.tickets.presentation.model.TicketScreenModel

class TicketsActivity : BaseActivity<TicketsPresenter>(), TicketsContract.View {

    private val pullToRefreshView by lazy { findViewById<SwipeRefreshLayout>(R.id.ticketsPullToRefresh) }
    private val emptyView by lazy { findViewById<ViewGroup>(R.id.ticketsEmpty) }
    private var ticketsAdapter = TicketsAdapter()

    override fun injectDependencies() {
        DaggerTicketsFeatureComponent.builder()
                .applicationComponent((application as ClientSupportApplication).applicationComponent)
                .ticketsFeatureModule(TicketsFeatureModule(this))
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets)

        initialiseRefreshAction()
        initialiseTicketsView()

        presenter.loadTicketsForView(BuildConfig.REMOTE_VIEW_ID)
    }

    override fun displayProgress() {
        pullToRefreshView.isRefreshing = true
    }

    override fun hideProgress() {
        pullToRefreshView.isRefreshing = false
    }

    override fun hideEmptyState() {
        emptyView.visibility = View.GONE
    }

    override fun showEmptyState() {
        emptyView.visibility = View.VISIBLE
    }

    override fun showTickets(tickets: List<TicketScreenModel>) {
        ticketsAdapter.updateTickets(tickets)
    }

    override fun showError() {
        Snackbar.make(findViewById(R.id.ticketsList), R.string.tickets_generic_error, Snackbar.LENGTH_LONG).show()
    }

    private fun initialiseRefreshAction() {
        pullToRefreshView.setOnRefreshListener { presenter.refreshTickets(BuildConfig.REMOTE_VIEW_ID) }
        pullToRefreshView.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        displayProgress()
    }

    private fun initialiseTicketsView() {
        val ticketsListView = findViewById<RecyclerView>(R.id.ticketsList)
        ticketsListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ticketsListView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        ticketsAdapter = TicketsAdapter()
        ticketsListView.adapter = ticketsAdapter
    }
}
