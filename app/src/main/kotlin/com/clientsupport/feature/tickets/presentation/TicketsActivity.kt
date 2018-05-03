package com.clientsupport.feature.tickets.presentation

import android.os.Bundle
import com.clientsupport.BuildConfig
import com.clientsupport.R
import com.clientsupport.core.ClientSupportApplication
import com.clientsupport.core.structure.BaseActivity
import com.clientsupport.feature.tickets.di.DaggerTicketsFeatureComponent
import com.clientsupport.feature.tickets.di.TicketsFeatureModule

class TicketsActivity : BaseActivity<TicketsPresenter>(), TicketsContract.View {

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

        presenter.loadTicketsForView(BuildConfig.REMOTE_VIEW_ID)
    }
}
