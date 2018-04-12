package com.migcapps.smparking.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.migcapps.smparking.R
import com.migcapps.smparking.model.Lot
import com.migcapps.smparking.model.MapsInteractorImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val mLotsArrayList = ArrayList<Lot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapsInteractor = MapsInteractorImpl()
        mapsInteractor.requestLots()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    lot -> mLotsArrayList.add(lot)
                },{
                    e -> e.printStackTrace()
                },{
                    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                    val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)
                })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        for (lot in mLotsArrayList){
            val location = LatLng(lot.latitude, lot.longitude)
            mMap.addMarker(MarkerOptions().position(location).title(lot.availableSpaces.toString()))
        }

    }
}
