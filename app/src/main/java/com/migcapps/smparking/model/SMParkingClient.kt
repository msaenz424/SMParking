package com.migcapps.smparking.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface SMParkingClient {

    @Headers("Accept: application/json")
    @GET("lots")
    fun getLotResult(): Observable<List<Lot>>

}