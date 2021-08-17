package com.camilobaquero.mytaxitest.data

import com.google.gson.annotations.SerializedName

data class VehicleModel(
    @SerializedName("id") val id: Long,
    @SerializedName("coordinate") val coordinate: CoordinateEntity,
    @SerializedName("fleetType") val fleetType: FleetTypeEnum,
    @SerializedName("heading") val heading: Double,
)
