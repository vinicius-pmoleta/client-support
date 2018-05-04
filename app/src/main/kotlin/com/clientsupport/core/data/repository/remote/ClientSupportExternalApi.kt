package com.clientsupport.core.data.repository.remote

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface ClientSupportExternalApi {

    @GET("api/v2/views/{viewId}/tickets.json")
    fun getTicketsForView(@Path("viewId") viewId: Long): Flowable<TicketsResponse>
}