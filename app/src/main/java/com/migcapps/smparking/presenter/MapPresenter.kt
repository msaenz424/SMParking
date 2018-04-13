package com.migcapps.smparking.presenter

import com.google.android.gms.maps.GoogleMap

interface MapPresenter {

    fun onMapReady(googleMap: GoogleMap)

}