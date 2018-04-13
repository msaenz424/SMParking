package com.migcapps.smparking.model

import io.reactivex.Observable

interface MapsInteractor {

    fun requestLots(): Observable<Lot>

    fun addLot(lot: Lot)

    fun getLotsList(): ArrayList<Lot>

}