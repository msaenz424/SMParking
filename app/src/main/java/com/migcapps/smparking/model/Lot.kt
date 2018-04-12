package com.migcapps.smparking.model

import com.google.gson.annotations.SerializedName

class Lot(val id: Int,
          val latitude: Double,
          val longitude: Double,
          @SerializedName("available_spaces") val availableSpaces: Int)