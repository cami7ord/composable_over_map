package com.camilobaquero.mytaxitest.repository

import com.camilobaquero.mytaxitest.api.ApiHelper
import javax.inject.Inject

class VehiclesRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getVehicles() = apiHelper.getVehicles()
}
