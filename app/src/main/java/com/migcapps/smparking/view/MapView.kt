package com.migcapps.smparking.view

import com.google.android.gms.maps.GoogleMap
import com.migcapps.smparking.model.Lot

interface MapView {

    fun displayParkingStructures(lotsArrayList: ArrayList<Lot>, googleMap: GoogleMap)

}