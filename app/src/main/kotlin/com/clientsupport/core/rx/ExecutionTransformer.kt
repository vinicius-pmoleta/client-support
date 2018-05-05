package com.clientsupport.core.rx

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import org.reactivestreams.Publisher

data class ExecutionConfiguration(
        val executionScheduler: Scheduler,
        val postExecutionScheduler: Scheduler
)

class ExecutionTransformer<T>(
        private val configuration: ExecutionConfiguration) : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream
                .subscribeOn(configuration.executionScheduler)
                .observeOn(configuration.postExecutionScheduler)
    }
}