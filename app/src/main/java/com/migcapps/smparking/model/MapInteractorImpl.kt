package com.migcapps.smparking.model

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class MapInteractorImpl : MapInteractor {

    private val mSMParkingClient: SMParkingClient
    private val mLotsArrayList = ArrayList<Lot>()

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://parking.api.smgov.net/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mSMParkingClient = retrofit.create(SMParkingClient::class.java)
    }

    override fun requestLots(): Observable<Lot> {
        val observable: Observable<Lot>
        observable = mSMParkingClient.getLotResult()
                .flatMap { lotResult -> Observable.fromIterable(lotResult) }
                .map { lot -> Lot(lot.id, lot.latitude, lot.longitude, lot.availableSpaces) }

        return observable
    }

    override fun addLot(lot: Lot) {
        mLotsArrayList.add(lot)
    }

    override fun getLotsList(): ArrayList<Lot> {
        return mLotsArrayList
    }

}