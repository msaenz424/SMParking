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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.migcapps.smparking.presenter.MapPresenterImpl
import java.util.*
import kotlin.collections.ArrayList

class MapActivity : AppCompatActivity(), MapView, OnMapReadyCallback {

    private val ZOOM_LEVEL = 14
    private val ZOOM_DURATION = 1
    private val SM_DEFAULT_LATITUDE = 34.014539
    private val SM_DEFAULT_LONGITUDE = -118.498640
    private lateinit var mMapPresenter: MapPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        loadBannerAd()
        title = resources.getString(R.string.title_activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mMapPresenter = MapPresenterImpl(this)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun displayParkingStructures(lotsArrayList: ArrayList<Lot>, googleMap: GoogleMap) {
        for (lot in lotsArrayList){
            val marker = googleMap.addMarker(generateMarker(lot))
            marker.title = lot.name
            marker.snippet = lot.streetAddress
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(SM_DEFAULT_LATITUDE, SM_DEFAULT_LONGITUDE)))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_LEVEL.toFloat()), ZOOM_DURATION, null)
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

    /**
     * Initializes and loads banner ad
     */
    private fun loadBannerAd(){
        MobileAds.initialize(this, resources.getString(R.string.map_banner_ad_unit_id))
        val adView: AdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}
