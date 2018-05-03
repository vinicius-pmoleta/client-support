package com.clientsupport.feature.tickets.di

import com.clientsupport.core.di.FeatureScope
import com.clientsupport.core.di.component.ApplicationComponent
import com.clientsupport.feature.tickets.presentation.TicketsActivity
import dagger.Component

@FeatureScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [TicketsFeatureModule::class]
)
interface TicketsFeatureComponent {

    fun inject(activity: TicketsActivity)
}