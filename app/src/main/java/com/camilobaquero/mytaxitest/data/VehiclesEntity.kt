package com.camilobaquero.mytaxitest.data

import com.google.gson.annotations.SerializedName

data class VehiclesEntity(
    @SerializedName("poiList") val data: List<VehicleModel>? = null
)
