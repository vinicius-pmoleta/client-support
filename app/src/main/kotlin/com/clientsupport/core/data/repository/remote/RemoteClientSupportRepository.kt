package com.clientsupport.core.data.repository.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteClientSupportRepository {

    @GET("api/v2/views/{viewId}/tickets.json")
    fun getTicketsFromView(@Path("viewId") viewId: Int) : Observable<Unit>
}