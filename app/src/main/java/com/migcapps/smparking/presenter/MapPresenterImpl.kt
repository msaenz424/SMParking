package com.migcapps.smparking.presenter

import com.google.android.gms.maps.GoogleMap
import com.migcapps.smparking.model.MapsInteractorImpl
import com.migcapps.smparking.view.MapView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapPresenterImpl constructor(mapView: MapView) : MapPresenter{

    val mMapView = mapView
    val mMapInteractor = MapsInteractorImpl()

    override fun onMapReady(googleMap: GoogleMap) {
        val mapsInteractor = MapsInteractorImpl()
        mapsInteractor.requestLots()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    lot -> mMapInteractor.addLot(lot)
                },{
                    e -> e.printStackTrace()
                },{
                    mMapView.displayParkingStructures(mMapInteractor.getLotsList(), googleMap)
                })
    }

}