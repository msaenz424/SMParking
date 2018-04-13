package com.migcapps.smparking.model

import io.reactivex.Observable

interface MapInteractor {

    fun requestLots(): Observable<Lot>

    fun addLot(lot: Lot)

    fun getLotsList(): ArrayList<Lot>

}