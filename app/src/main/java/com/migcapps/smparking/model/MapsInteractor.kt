package com.migcapps.smparking.model

import io.reactivex.Observable

interface MapsInteractor {

    fun requestLots(): Observable<Lot>

}