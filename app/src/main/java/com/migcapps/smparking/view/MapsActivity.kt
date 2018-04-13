package com.migcapps.smparking.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.migcapps.smparking.R
import com.migcapps.smparking.model.Lot
import com.google.maps.android.ui.IconGenerator
import android.widget.TextView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.migcapps.smparking.presenter.MapPresenterImpl
import java.util.*
import kotlin.collections.ArrayList

class MapsActivity : AppCompatActivity(), MapView, OnMapReadyCallback {

    private lateinit var mMapPresenter: MapPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mMapPresenter = MapPresenterImpl(this)
    }

    override fun displayParkingStructures(lotsArrayList: ArrayList<Lot>, googleMap: GoogleMap) {
        for (lot in lotsArrayList){
            googleMap.addMarker(generateMarker(lot))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMapPresenter.onMapReady(googleMap)
    }

    /**
     * Creates a marker with the number of spaces available
     * in the parking structure.
     * @param lot a Lot object which represents a parking structure
     *
     * @return a marker icon
     */
    private fun generateMarker(lot: Lot) : MarkerOptions{
        val parkingLocation = LatLng(lot.latitude, lot.longitude)

        val availableSpacesTextView = TextView(this)
        availableSpacesTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        availableSpacesTextView.text = lot.availableSpaces.toString()

        val iconGenerator = IconGenerator(this)
        iconGenerator.setContentView(availableSpacesTextView)
        iconGenerator.setStyle(pickRandomStyle())
        iconGenerator.setContentPadding(
                resources.getInteger(R.integer.marker_text_horizontal_padding),
                0,
                resources.getInteger(R.integer.marker_text_horizontal_padding),
                0)
        val icon = iconGenerator.makeIcon()

        return MarkerOptions().position(parkingLocation).icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    /**
     * Picks a random number between 4 and 7
     * Current constant values as per doc (http://googlemaps.github.io/android-maps-utils/javadoc/com/google/maps/android/ui/IconGenerator.html):
     * STYLE_DEFAULT    1
     * STYLE_WHITE      2
     * STYLE_RED        3
     * STYLE_BLUE       4
     * STYLE_GREEN      5
     * STYLE_PURPLE     6
     * STYLE_ORANGE     7
     */
    private fun pickRandomStyle(): Int {
        val max = IconGenerator.STYLE_ORANGE
        val min = IconGenerator.STYLE_BLUE
        val random = Random()
        return random.nextInt((max - min) + 1) + min
    }
}
